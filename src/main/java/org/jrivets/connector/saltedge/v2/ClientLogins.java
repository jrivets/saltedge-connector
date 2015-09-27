package org.jrivets.connector.saltedge.v2;

public class ClientLogins {

    // number of registered logins
    public Integer total;
    
    // number of active logins, will be shown if active logins exist
    public Integer active;
    
    // number of inactive logins, will be shown if inactive logins exist
    public Integer inactive;
    
    // number of disabled logins, will be shown if disabled logins exist
    public Integer disabled;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("total", total).append("active", active).append("inactive", inactive)
                .append("disabled", disabled);
        return builder.toString();
    }
}
