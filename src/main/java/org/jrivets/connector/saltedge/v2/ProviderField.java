package org.jrivets.connector.saltedge.v2;

import java.util.List;

public final class ProviderField {

    public FieldNature nature;
    
    public String name;
    
    public String english_name;
    
    public String localized_name;
    
    public Integer position;
    
    public Boolean optional;
    
    public List<ProviderFieldOptions> field_options;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("nature", nature).append("name", name).append("english_name", english_name)
                .append("localized_name", localized_name).append("position", position).append("optional", optional)
                .append("field_options", field_options);
        return builder.toString();
    }
}
