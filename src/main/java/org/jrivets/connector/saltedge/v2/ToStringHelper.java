package org.jrivets.connector.saltedge.v2;

import java.util.Arrays;

public final class ToStringHelper {

    private final StringBuilder sb = new StringBuilder();
    
    private boolean notFirst;
    
    public ToStringHelper(Object o) {
        sb.append("{");
    }
    
    public ToStringHelper append(String name, Object[] value) {
        if (value != null) {
            return append(name, Arrays.toString(value));
        }
        return this;
    }
    
    public ToStringHelper append(String name, Object value) {
        if (value != null) {
            if (notFirst) {
                sb.append(", ");
            }
            sb.append(name).append('=').append(value);
            notFirst = true;
        }
        return this;
    }
    
    @Override
    public String toString() {
        return sb.toString();
    }
}
