package org.jrivets.connector.saltedge.v2;

import java.util.Date;

public class Account extends IdentifiedObject {

    // the unique name of the account
    public String name;

    // the type of the account. Possible values are: account, card, credit_card,
    // debit_card, checking, savings, investment, bonus, loan, credit,
    // insurance. Note that for credit_card nature, the balance represents the
    // sum of all negative transactions, the positive ones do not count.
    public String nature;

    // the account’s current balance
    public String balance;

    // one of the possible values for currency codes. Max 3 letters
    public String currency_code;

    // extra data associated with the account
    public AccountExtra extra;

    // the id of the login the account belongs to
    public Integer login_id;

    public Date created_at;

    public Date updated_at;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("id", id).append("name", name).append("nature", nature).append("balance", balance)
                .append("currency_code", currency_code).append("extra", extra).append("login_id", login_id)
                .append("created_at", created_at).append("updated_at", updated_at);
        return builder.toString();
    }
}
