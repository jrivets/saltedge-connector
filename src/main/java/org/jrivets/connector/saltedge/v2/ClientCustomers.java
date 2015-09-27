package org.jrivets.connector.saltedge.v2;

public class ClientCustomers {

    public Integer total;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("total", total);
        return builder.toString();
    }
}
