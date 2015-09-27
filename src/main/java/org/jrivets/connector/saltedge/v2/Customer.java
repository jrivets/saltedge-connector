package org.jrivets.connector.saltedge.v2;

public final class Customer extends IdentifiedObject {
   
    public String identifier;
    
    public String customer_id;
    
    public String secret;
    
    public Boolean deleted;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("id", id).append("identifier", identifier).append("customer_id", customer_id)
                .append("secret", secret).append("deleted", deleted);
        return builder.toString();
    }
    
}
