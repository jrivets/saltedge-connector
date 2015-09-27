package org.jrivets.connector.saltedge.v2;

import java.util.Date;

public class DateOnly extends Date {

    private static final long serialVersionUID = 636450584602197344L;

    public DateOnly() {
        super();
    }
    
    public DateOnly(Date date) {
        super(date.getTime());
    }
    
}
