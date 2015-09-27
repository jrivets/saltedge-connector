package org.jrivets.connector.saltedge.v2;

import java.util.Date;

public class TransactionQuery {
    
    // the id of the account
    Integer account_id;

    // the id of the login
    Integer login_id;

    // the id from which the next page of transactions starts
    Integer from_id;

    // the date from which the transactions should be listed
    Date from_date;

    // date, optional
    Date to_date;
    
    Integer limit;
    
    public TransactionQuery() {
    }
    
    public TransactionQuery accountId(int accountId) {
        this.account_id = accountId;
        return this;
    }
    
    public TransactionQuery loginId(int loginId) {
        this.login_id = loginId;
        return this;
    }
    
    public TransactionQuery fromId(int fromId) {
        this.from_id = fromId;
        return this;
    }
    
    public TransactionQuery fromDate(Date fromDate) {
        this.from_date = fromDate;
        return this;
    }
    
    public TransactionQuery toDate(Date toDate) {
        this.to_date = toDate;
        return this;
    }
    
    public TransactionQuery limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("account_id", account_id).append("login_id", login_id).append("from_id", from_id)
                .append("from_date", from_date).append("to_date", to_date).append("limit", limit);
        return builder.toString();
    }
}
