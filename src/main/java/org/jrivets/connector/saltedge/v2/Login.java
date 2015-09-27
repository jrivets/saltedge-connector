package org.jrivets.connector.saltedge.v2;

import java.util.Date;
import java.util.Map;

public final class Login extends IdentifiedObject {

    // token used to identify mobile applications, to be used as LOGIN_SECRET in
    // the mobile requests. For more info consult Mobile SDKs
    public String secret;

    // whether all the data was fetched for this login
    public Boolean finished;

    // set to true when the login has finished fetching recent transactions
    public Boolean finished_recent;

    // whether only partial data is available
    public Boolean partial;

    // whether the login can be refreshed
    public Boolean automatic_fetch;

    // whether the login requires any interactive credentials
    public Boolean interactive;

    // the code of the Provider the login belongs to
    public String provider_code;

    // the name of the Provider the login belongs to
    public String provider_name;

    // customer’s ID
    public String customer_id;

    public Date created_at;

    public Date updated_at;

    // time when the login received last failure
    public Date last_fail_at;

    // the message of the fail that occurred at last_fail_at
    public String last_fail_message;

    // the class name of the fail that occurred at last_fail_at.
    public Error last_fail_error_class;

    // time when login’s data change occured after success or fail
    public Date last_request_at;

    // time when the login was successfully fetched
    public Date last_success_at;

    // possible values are: active, inactive, disabled
    public LoginStatus status;

    // code of the country the provider belongs to
    public String country_code;

    // HTML code that shows the current interactive state of the login. Appears
    // only for interactive logins
    public String interactive_html;

    // the interactive fields that are currently required by the provider
    public String[] interactive_fields_names;

    // the current stage of the login
    public String stage;

    // whether the credentials are stored on SaltEdge side
    public Boolean store_credentials;
    
    // the the credentials required to access the data
    public Map<String, Object> credentials;
    
    // fetching mode, possible values: recent, accounts. Default value: recent.
    public LoginFetchType fetch_type;

    // shows whether it would be possible to reconnect a fake provider or not in live mode. Defaults to false
    public Boolean include_fake_providers;

    // the flag allows you to specify whether the transactions of this login should be categorized or not. Defaults to true
    public Boolean categorize;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("id", id).append("secret", secret).append("finished", finished)
                .append("finished_recent", finished_recent).append("partial", partial)
                .append("automatic_fetch", automatic_fetch).append("interactive", interactive)
                .append("provider_code", provider_code).append("provider_name", provider_name)
                .append("customer_id", customer_id).append("created_at", created_at).append("updated_at", updated_at)
                .append("last_fail_at", last_fail_at).append("last_fail_message", last_fail_message)
                .append("last_fail_error_class", last_fail_error_class).append("last_request_at", last_request_at)
                .append("last_success_at", last_success_at).append("status", status)
                .append("country_code", country_code).append("interactive_html", interactive_html)
                .append("interactive_fields_names", interactive_fields_names).append("stage", stage)
                .append("store_credentials", store_credentials).append("credentials", credentials)
                .append("fetch_type", fetch_type).append("include_fake_providers", include_fake_providers)
                .append("categorize", categorize);
        return builder.toString();
    }
    
}
