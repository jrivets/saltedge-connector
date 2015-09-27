package org.jrivets.connector.saltedge.v2;

import java.util.Date;

public class ClientInfo {
    //possible values: pending, disabled, test, live, restricted
    public ClientStatus status;

    // time when the client signed up
    public Date signed_up_at;

    // time when the client became live
    public Date activated_at;

    // time when last request was sent
    public Date last_request_sent_at;

    // time when last callback was received
    public Date last_callback_received_at;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("status", status).append("signed_up_at", signed_up_at).append("activated_at", activated_at)
                .append("last_request_sent_at", last_request_sent_at)
                .append("last_callback_received_at", last_callback_received_at);
        return builder.toString();
    }

}
