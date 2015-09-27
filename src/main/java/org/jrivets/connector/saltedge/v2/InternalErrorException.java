package org.jrivets.connector.saltedge.v2;

public class InternalErrorException extends RuntimeException {

    private static final long serialVersionUID = -7310637004793197798L;

    InternalErrorException(Exception cause) {
        super(cause);
    }
    
    InternalErrorException(String msg) {
        super(msg);
    }
}
