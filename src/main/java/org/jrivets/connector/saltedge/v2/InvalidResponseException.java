package org.jrivets.connector.saltedge.v2;

public class InvalidResponseException extends RuntimeException {

    private static final long serialVersionUID = 598646792000913163L;

    private final int responseCode;
    
    private final ErrorResponse er;

    public InvalidResponseException(int responseCode, ErrorResponse er) {
        this.responseCode = responseCode;
        this.er = er;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public ErrorResponse getErrorResponse() {
        return er;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{responseCode=").append(responseCode).append(", errorResponce=").append(er).append("}");
        return builder.toString();
    }
    
}
