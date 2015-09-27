package org.jrivets.connector.saltedge.v2;

import java.util.Date;

public class Transaction extends IdentifiedObject {
   
    //  possible values are: normal, fee, transfer
    public TransactionMode mode;

    // possible values are: posted, pending
    public TransactionStatus status;

    // the date when the transaction was made
    public DateOnly made_on;

    // transaction’s amount
    public Double amount;

    // transaction’s currency code
    public String currency_code;

    // transaction’s description
    public String description;

    // transaction’s category
    public String category;

    // whether the transaction is duplicated or not
    public Boolean duplicated;

    // extra data associated with the transaction
    public TransactionExtra extra;

    // the id of the account the transaction belongs to
    public Integer account_id;

    public Date created_at;
    
    public Date updated_at;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("id", id).append("mode", mode).append("status", status).append("made_on", made_on)
                .append("amount", amount).append("currency_code", currency_code).append("description", description)
                .append("category", category).append("duplicated", duplicated).append("extra", extra)
                .append("account_id", account_id).append("created_at", created_at).append("updated_at", updated_at);
        return builder.toString();
    }
}
