package org.jrivets.connector.saltedge.v2;

import java.util.Date;
import java.util.List;

public final class Provider extends IdentifiedObject {
    
    public String code;

    public String name;

    public ProviderMode mode;

    public ProviderStatus status;

    // whether the provider’s logins can be automatically fetched
    public Boolean automatic_fetch;

    //whether the provider requires interactive input
    public Boolean interactive;
    
    //instructions on how to connect the bank, in English
    public String instruction;

    //the url of the main page of the provider
    public String home_url;

    //point of entrance to provider’s login web interface
    public String login_url;

    //the url for the Salt Edge Forum page, dedicated to the provider
    public String forum_url;

    //code of the provider’s country
    public String country_code;

    //amount of time (in minutes) after which the provider’s logins are allowed to be refreshed
    public Integer refresh_timeout;
    
    public List<ProviderField> required_fields;

    public Date created_at;

    public Date updated_at;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("id", id).append("code", code).append("name", name).append("mode", mode)
                .append("status", status).append("automatic_fetch", automatic_fetch).append("interactive", interactive)
                .append("instruction", instruction).append("home_url", home_url).append("login_url", login_url)
                .append("forum_url", forum_url).append("country_code", country_code)
                .append("refresh_timeout", refresh_timeout).append("required_fields", required_fields)
                .append("created_at", created_at).append("updated_at", updated_at);
        return builder.toString();
    }
}
