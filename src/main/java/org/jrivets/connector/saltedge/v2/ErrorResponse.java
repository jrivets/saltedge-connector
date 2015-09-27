package org.jrivets.connector.saltedge.v2;

public class ErrorResponse {

    // the class of the error, one of the listed below
    public Error error_class;

    // a message describing the error
    public String message;

    public Object request;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("error_class", error_class).append("message", message).append("request", request);
        return builder.toString();
    }
}
