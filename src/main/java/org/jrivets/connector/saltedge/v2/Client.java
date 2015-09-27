package org.jrivets.connector.saltedge.v2;

public class Client {

    public ClientSettings settings;
    
    public ClientInfo info;
    
    public ClientCustomers customers;
    
    public ClientLogins logins;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("settings", settings).append("info", info).append("customers", customers)
                .append("logins", logins);
        return builder.toString();
    }

}
