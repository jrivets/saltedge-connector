package org.jrivets.connector.saltedge.v2;

import java.util.Date;

public class TransactionExtra extends IdentifiedObject {

    // bank record number
    public String record_number;
    
    // information about the transaction
    public String information;
    
    // time when the transaction was made
    public String time;

    // date when the transaction appears in statement
    public DateOnly posting_date;

    // time when the transaction appears in statement
    public Date posting_time;
    
    // number of the account the transaction belongs to
    public String account_number;

    // native amount of the transaction in transaction’s currency (comes with original_currency_code)
    public Double original_amount;

    // native currency of the transaction (comes with original_amount)
    public String original_currency_code;

    // the original category of the transaction
    public String original_category;

    // the original subcategory of the transaction
    public String original_subcategory;
    
    // the original tags of the transaction
    public String[] tags;
    
    // the transaction’s Merchant Category Code
    public Integer mcc;
    
    // to whom money is paid
    public String payee;

    // transaction type
    public String type;
    
    // payee’s transaction check number
    public String check_number;
    
    // amount of units owned (used with unit_price, available for investment accounts nature only)
    public Double units;
    
    // additional information (recommended to use in concatenation with original description, if present)
    public String additional;
    
    // price per unit (used with units, available for investment accounts nature only)
    public Double unit_price;

    @Override
    public String toString() {
        ToStringHelper builder = new ToStringHelper(this);
        builder.append("id", id).append("record_number", record_number).append("information", information)
                .append("time", time).append("posting_date", posting_date).append("posting_time", posting_time)
                .append("account_number", account_number).append("original_amount", original_amount)
                .append("original_currency_code", original_currency_code)
                .append("original_category", original_category).append("original_subcategory", original_subcategory)
                .append("tags", tags).append("mcc", mcc).append("payee", payee).append("type", type)
                .append("check_number", check_number).append("units", units).append("additional", additional)
                .append("unit_price", unit_price);
        return builder.toString();
    }
    
}
