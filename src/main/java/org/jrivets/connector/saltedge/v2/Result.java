package org.jrivets.connector.saltedge.v2;

import java.util.List;

public class Result<T> {
    
    public List<T> data;
    
    public ResultMeta meta;

    @Override
    public String toString() {
        return "{data=" + data + ", meta=" + meta + "}";
    }
}
