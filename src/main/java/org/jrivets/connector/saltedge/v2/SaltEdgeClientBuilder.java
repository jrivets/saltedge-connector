package org.jrivets.connector.saltedge.v2;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class SaltEdgeClientBuilder {

    private int maxConnections = 100;

    private int socketTimeout = 10000;

    private int connectionTimeout = 10000;

    private String endpoint = "https://www.saltedge.com";

    private String apiPath = "/api/v2/";

    private boolean trustAll;

    private String clientId;

    private String serviceSecret;

    private String prvtKeyFileName;

    private long signatureExpTimeout = TimeUnit.SECONDS.toMillis(60);

    public SaltEdgeClientBuilder maxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    public SaltEdgeClientBuilder socketTimeoutMilis(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public SaltEdgeClientBuilder connectionTimeoutMilis(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public SaltEdgeClientBuilder trustAll(boolean trustAll) {
        this.trustAll = trustAll;
        return this;
    }

    public SaltEdgeClientBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public SaltEdgeClientBuilder apiPath(String apiPath) {
        this.apiPath = apiPath;
        return this;
    }

    public SaltEdgeClientBuilder clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaltEdgeClientBuilder serviceSecret(String serviceSecret) {
        this.serviceSecret = serviceSecret;
        return this;
    }

    public SaltEdgeClientBuilder privateKeyFileName(String prvtKeyFileName) {
        this.prvtKeyFileName = prvtKeyFileName;
        return this;
    }

    public SaltEdgeClientBuilder signatureExpTimeoutSec(int signatureExpTimeout) {
        this.signatureExpTimeout = TimeUnit.SECONDS.toMillis(signatureExpTimeout);
        return this;
    }

    public SaltEdgeClient build() {
        assertNotNull(clientId, "Client ID should not be null");
        assertNotNull(serviceSecret, "ServiceSecret should not be null");
        Signer signer = createSigner();
        HttpClient httpClient = newHttpClient();
        return new DefaultSaltEdgeClient(httpClient, endpoint, apiPath,
                clientId, serviceSecret, signer, signatureExpTimeout);
    }

    private Signer createSigner() {
        if (StringUtils.isBlank(prvtKeyFileName)) {
            return null;
        }
        try {
            return new Signer(prvtKeyFileName);
        } catch (Exception ex) {
            throw new InternalErrorException(ex);
        }
    }

    private void assertNotNull(Object o, String message) {
        if (o == null) {
            throw new NullPointerException(message);
        }
    }
    
    private HttpClient newHttpClient() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 8080, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, trustAll ? buildTrustAllSSLSocketFactory() :
                SSLSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
        connectionManager.setDefaultMaxPerRoute(maxConnections);
        connectionManager.setMaxTotal(maxConnections);

        HttpParams httpParams = new BasicHttpParams();
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);

        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, "UTF-8");

        DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager);
        httpClient.setParams(httpParams);
        return httpClient; 
    }

    private SSLSocketFactory buildTrustAllSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new SecureRandom());

            return new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            return null;
        }
    }

}
