package org.jrivets.connector.saltedge.v2;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

public final class Utils {

    static String toString(HttpRequestBase request) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(request.getRequestLine());
        for (Header h: request.getAllHeaders()) {
            sb.append("\n    ");
            sb.append(h);
        }
        if (request instanceof HttpEntityEnclosingRequestBase) {
            sb.append("\n\n");
            try {
                sb.append(EntityUtils.toString(((HttpEntityEnclosingRequestBase) request).getEntity()));
            } catch (Exception e) {
                sb.append("<<< Could not parse body >>>");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    
    static void assertNotNullParam(Object o, String msg) {
        if (o == null) {
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static Map<String, Object> list2Map(Object... params) {
        if (params.length == 0) {
            return null;
        }

        if ((params.length & 1) == 1) {
            throw new IllegalArgumentException("Expect even number of params, but got " + params.length);
        }

        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < params.length; i += 2) {
            String key = (String) params[i];
            result.put(key, params[i + 1]);
        }
        return result;
    }
}
