package org.jrivets.connector.saltedge.v2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.common.reflect.TypeToken;

final class DefaultSaltEdgeClient implements SaltEdgeClient {

    private Logger logger = Logger.getLogger(DefaultSaltEdgeClient.class);

    private static final String HDR_CLIENT_ID = "Client-id";

    private static final String HDR_SERVICE_SECRET = "Service-secret";

    private static final String HDR_SIGNATURE = "Signature";

    private static final String HDR_EXPIRES_AT = "Expires-at";

    private static final String HDR_CONTENT_TYPE = "Content-Type";

    private final Serializer serializer = new Serializer();

    private final String endpoint;

    private final String apiPath;

    private final String clientId;

    private final String serviceSecret;

    private final Signer signer;

    private final long signatureExpTimeout;

    private final HttpClient httpClient;

    private static class Data<T> {
        T data;
    }

    private static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

        public final static String METHOD_NAME = "DELETE";

        private HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }
    
    static class LoginDeleteResponse {
        Integer id;
        Boolean removed;
    }

    DefaultSaltEdgeClient(HttpClient httpClient, String endpoint, String apiPath, String clientId,
            String serviceSecret, Signer signer, long signatureExpTimeout) {
        this.endpoint = endpoint;
        this.apiPath = apiPath;
        this.clientId = clientId;
        this.serviceSecret = serviceSecret;
        this.signer = signer;
        this.signatureExpTimeout = signatureExpTimeout;
        this.httpClient = httpClient;
    }

    @Override
    public List<String> getCountryCodes() {
        HttpResponse response = get("countries");
        Data<List<String>> dc = serializer.fromJson(getBody(response), new TypeToken<Data<List<String>>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }

    @Override
    public Client getClientInfo() {
        HttpResponse response = get("client/info");
        Data<Client> dc = serializer.fromJson(getBody(response), new TypeToken<Data<Client>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }

    @Override
    public Provider getProviderByCode(String providerCode) {
        HttpResponse response = get("providers/" + providerCode);
        String body = getBody(response);
        Data<Provider> dc = serializer.fromJson(body, new TypeToken<Data<Provider>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }

    @Override
    public Result<Provider> getProviders(int startId, int limit, Date fromDate) {
        checkLimit(limit);
        HttpResponse response = fromDate == null ? get("providers", "from_id", startId, "per_page", limit) : get(
                "providers", "from_id", startId, "per_page", limit, "from_date", fromDate);
        String body = getBody(response);
        Result<Provider> result = serializer.fromJson(body, new TypeToken<Result<Provider>>() {
            private static final long serialVersionUID = 1L;
        }.getType());

        adjustLimit(result, limit);
        return result;
    }

    @Override
    public Customer createCustomer() {
        Customer c = new Customer();
        c.identifier = UUID.randomUUID().toString();
        Data<Customer> data = new Data<>();
        data.data = c;
        String body = serializer.toJson(data);
        HttpResponse response = post("customers", body);
        Data<Customer> dc = serializer.fromJson(getBody(response), new TypeToken<Data<Customer>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }

    @Override
    public Result<Customer> getCustomers(int startId, int limit) {
        checkLimit(limit);
        HttpResponse response = get("customers", "from_id", startId, "per_page", limit);
        String body = getBody(response);
        Result<Customer> result = serializer.fromJson(body, new TypeToken<Result<Customer>>() {
            private static final long serialVersionUID = 1L;
        }.getType());

        adjustLimit(result, limit);
        return result;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Customer c = new Customer();
        c.customer_id = customerId;
        Data<Customer> data = new Data<>();
        data.data = c;
        String body = serializer.toJson(data);
        HttpResponse response = delete("customers", body);
        Data<Customer> dc = serializer.fromJson(getBody(response), new TypeToken<Data<Customer>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data.deleted != null ? dc.data.deleted : false;
    }
    
    @Override
    public Login createLogin(Login login) {
        Utils.assertNotNullParam(login.customer_id, "Required field customer_id cannot be null");
        Utils.assertNotNullParam(login.country_code, "Required field country_code cannot be null");
        Utils.assertNotNullParam(login.provider_code, "Required field provider_code cannot be null");
        Utils.assertNotNullParam(login.credentials, "Required field credentials cannot be null");
        Data<Login> data = new Data<>();
        data.data = login;
        String body = serializer.toJson(data);
        HttpResponse response = post("logins", body);
        Data<Login> dc = serializer.fromJson(getBody(response), new TypeToken<Data<Login>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }
    
    @Override
    public Login reconnectLogin(Login login) {
        Utils.assertNotNullParam(login.id, "Required field id cannot be null");
        Utils.assertNotNullParam(login.credentials, "Required field credentials cannot be null");
        Data<Login> data = new Data<>();
        data.data = login;
        String body = serializer.toJson(data);
        HttpResponse response = put("logins/" + login.id + "/reconnect", body);
        Data<Login> dc = serializer.fromJson(getBody(response), new TypeToken<Data<Login>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }

    @Override
    public Login interactiveLogin(Login login) {
        Utils.assertNotNullParam(login.id, "Required field id cannot be null");
        Utils.assertNotNullParam(login.credentials, "Required field credentials cannot be null");
        Data<Login> data = new Data<>();
        data.data = login;
        String body = serializer.toJson(data);
        HttpResponse response = put("logins/" + login.id + "/interactive", body);
        Data<Login> dc = serializer.fromJson(getBody(response), new TypeToken<Data<Login>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }
    
    @Override
    public List<Login> getLogins(int startId, int limit) {
        checkLimit(limit);
        HttpResponse response = get("logins", "from_id", startId, "per_page", limit);
        String body = getBody(response);
        Result<Login> result = serializer.fromJson(body, new TypeToken<Result<Login>>() {
            private static final long serialVersionUID = 1L;
        }.getType());

        adjustLimit(result, limit);
        return result.data;
    }

    @Override
    public Login getLogin(int loginId) {
        HttpResponse response = get("logins/" + loginId);
        String body = getBody(response);
        Data<Login> dc = serializer.fromJson(body, new TypeToken<Data<Login>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data;
    }
    
    @Override
    public boolean deleteLogin(int loginId) {
        HttpResponse response = delete("logins/" + loginId, "");
        Data<LoginDeleteResponse> dc = serializer.fromJson(getBody(response), new TypeToken<Data<LoginDeleteResponse>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return dc.data.removed != null ? dc.data.removed : false;
    }
    
    @Override
    public List<Account> getAccounts(int loginId) {
        HttpResponse response = get("accounts", "login_id", loginId);
        String body = getBody(response);
        Data<List<Account>> result = serializer.fromJson(body, new TypeToken<Data<List<Account>>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return result.data;
    }

    @Override
    public List<String> getCurrencies() {
        HttpResponse response = get("currencies");
        String body = getBody(response);
        Data<List<String>> result = serializer.fromJson(body, new TypeToken<Data<List<String>>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return result.data;
    }

    @Override
    public Map<String, List<String>> getCategories() {
        HttpResponse response = get("categories");
        String body = getBody(response);
        Data<Map<String, List<String>>> result = serializer.fromJson(body, new TypeToken<Data<Map<String, List<String>>>>() {
            private static final long serialVersionUID = 1L;
        }.getType());
        return result.data;
    }

    @Override
    public Result<Transaction> getTransactions(TransactionQuery txQuery) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (txQuery.account_id != null) {
            params.put("account_id", txQuery.account_id);
        }
        if (txQuery.from_date != null) {
            params.put("from_date", txQuery.from_date);
        }
        if (txQuery.from_id != null) {
            params.put("from_id", txQuery.from_id);
        }
        if (txQuery.login_id != null) {
            params.put("login_id", txQuery.login_id);
        }
        if (txQuery.to_date != null) {
            params.put("to_date", txQuery.to_date);
        }
        int limit = 100;
        if (txQuery.limit != null) {
            limit = txQuery.limit;
            checkLimit(limit);
            params.put("limit", limit);
        }
        HttpResponse response = get("transactions", params);
        String body = getBody(response);
        Result<Transaction> result = serializer.fromJson(body, new TypeToken<Result<Transaction>>() {
            private static final long serialVersionUID = 1L;
        }.getType());

        adjustLimit(result, limit);
        return result;
    }
    
    @Override
    public void close() throws IOException {
        if (httpClient == null) {
            return;
        }

        ClientConnectionManager connectionManager = httpClient.getConnectionManager();
        if (connectionManager != null) {
            connectionManager.shutdown();
        }
    }

    private HttpResponse get(String path, Object... params) {
        return get(path, Utils.list2Map(params));
    }
    
    private HttpResponse get(String path, Map<String, Object> params) {
        URI uri = uri(path, params);
        HttpGet request = new HttpGet(uri);
        sign(request, "GET", uri.toString(), "");
        return execute(request);
    }

    private HttpResponse post(String path, String body) {
        URI uri = uri(path, null);
        HttpPost request = new HttpPost(uri);
        sign(request, "POST", uri.toString(), body);
        setBody(request, body);
        return execute(request);
    }
    
    private HttpResponse put(String path, String body) {
        URI uri = uri(path, null);
        HttpPut request = new HttpPut(uri);
        sign(request, "PUT", uri.toString(), body);
        setBody(request, body);
        return execute(request);
    }

    private HttpResponse delete(String path, String body) {
        URI uri = uri(path, null);
        HttpDeleteWithBody request = new HttpDeleteWithBody(uri);
        sign(request, "DELETE", uri.toString(), body);
        setBody(request, body);
        return execute(request);
    }

    private void setBody(HttpEntityEnclosingRequestBase request, String body) {
        request.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
    }

    private HttpResponse execute(HttpRequestBase request) {
        try {
            request.addHeader(HDR_CLIENT_ID, clientId);
            request.addHeader(HDR_SERVICE_SECRET, serviceSecret);
            request.addHeader(HDR_CONTENT_TYPE, "application/json");

            if (logger.isDebugEnabled()) {
                logger.debug("Request -->\n" + Utils.toString(request));
            }
            HttpResponse response = httpClient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            if (logger.isDebugEnabled()) {
                logger.debug("Response <-- " + response.getStatusLine());
            }
            if (responseCode / 100 != 2) {
                String body = getBody(response);
                logger.debug("Returned body: " + body);
                ErrorResponse er = serializer.fromJson(body, ErrorResponse.class);
                throw new InvalidResponseException(responseCode, er);
            }
            return response;
        } catch (IOException e) {
            throw new IllegalStateException("Problem communicating with endpoint.", e);
        }
    }

    private URI uri(String resourcePath, Map<String, Object> params) {
        try {
            URIBuilder builder = new URIBuilder(endpoint).setPath(apiPath + resourcePath);
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() != null) {
                        builder.addParameter(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
            return builder.build();
        } catch (URISyntaxException e) {
            throw new InternalErrorException(e);
        }
    }

    private String getBody(HttpResponse response) {
        if (response.getEntity() == null) {
            return null;
        }
        try {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }

    private void sign(HttpRequestBase request, String method, String url, String body) {
        if (signer == null) {
            return;
        }
        long expiresAt = System.currentTimeMillis() + signatureExpTimeout;
        String signature = getSignature(expiresAt, method, url, body);
        request.addHeader(HDR_EXPIRES_AT, String.valueOf(expiresAt));
        request.addHeader(HDR_SIGNATURE, signature);
    }

    private String getSignature(long expiresAt, String method, String url, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(expiresAt).append('|').append(method).append('|').append(url).append('|').append(body);
        try {
            return signer.sign(sb.toString());
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    private void checkLimit(int limit) {
        if (limit <= 0 || limit > 1000) {
            throw new IllegalArgumentException("Invalid argument limit=" + limit + ", it should be in [1..1000] range.");
        }
    }
    
    private <T extends IdentifiedObject> void adjustLimit(Result<T> result, int limit) {
        if (result.data != null && result.data.size() > limit) {
            int nextId = result.data.get(limit).id;
            result.data = result.data.subList(0, limit);
            result.meta.next_id = nextId;
        }
    }
}
