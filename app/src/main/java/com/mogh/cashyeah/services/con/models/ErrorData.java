package com.mogh.cashyeah.services.con.models;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class ErrorData
{
    public String getMessage()
    {
        return message;
    }

    private String message;

    public ErrorData(String message) {
        this.message = message;
    }
}
