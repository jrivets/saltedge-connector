package org.jrivets.connector.saltedge.v2;

public final class ProviderFieldOptions {

    public String name;
    
    public String english_name;
    
    public String localized_name;
    
    public Integer option_value;
    
    public Boolean selected;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("name", name).append("english_name", english_name).append("localized_name", localized_name)
                .append("option_value", option_value).append("selected", selected);
        return builder.toString();
    }
    
}
