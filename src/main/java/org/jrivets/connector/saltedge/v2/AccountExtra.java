package org.jrivets.connector.saltedge.v2;

import java.util.Date;

public class AccountExtra {

    // changeable name of the account
    public String account_name;

    // is the account active or inactive
    public AccountStatus status;

    // account client owner
    public String client_name;

    // account IBAN number
    public String iban;

    // account SWIFT code
    public String swift;

    // internal bank account number
    public String account_number;

    // the amount currently blocked in account’s currency
    public Double blocked_amount;

    // credit limit in account’s currency
    public Double available_amount;

    // credit limit in account’s currency
    public Double credit_limit;

    // interest rate of the account as percentage value
    public Double interest_rate;

    //card expiry date
    public Date expiry_date;

    // card open date
    public Date open_date;

    // time of provider statement generation (applicable to banks)
    public Date current_time;

    // date of provider statement generation (applicable to banks)
    public Date current_date;

    // list of masked card numbers
    public String[] cards;

    //amount of units owned (used with unit_price, available for investment accounts nature only)
    public Double units;

    //price per unit (used with units, available for investment accounts nature only)
    public Double unit_price;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("account_name", account_name).append("status", status).append("client_name", client_name)
                .append("iban", iban).append("swift", swift).append("account_number", account_number)
                .append("blocked_amount", blocked_amount).append("available_amount", available_amount)
                .append("credit_limit", credit_limit).append("interest_rate", interest_rate)
                .append("expiry_date", expiry_date).append("open_date", open_date).append("current_time", current_time)
                .append("current_date", current_date).append("cards", cards).append("units", units)
                .append("unit_price", unit_price);
        return builder.toString();
    }
}
