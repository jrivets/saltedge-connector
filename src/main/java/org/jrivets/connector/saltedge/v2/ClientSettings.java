package org.jrivets.connector.saltedge.v2;

public class ClientSettings {
    // email of the client
    public String email;
    
    // success callback URL
    public String success_url;
    
    // fail callback URL
    public String fail_url;

    // notify callback URL
    public String notify_url;

    // destroy callback URL
    public String destroy_url;

    // interactive callback URL
    public String interactive_url;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("email", email).append("success_url", success_url).append("fail_url", fail_url)
                .append("notify_url", notify_url).append("destroy_url", destroy_url)
                .append("interactive_url", interactive_url);
        return builder.toString();
    }
    
}
