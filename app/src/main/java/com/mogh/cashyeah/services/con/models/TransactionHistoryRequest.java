package com.mogh.cashyeah.services.con.models;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class TransactionHistoryRequest extends APIRequestWithToken
{
    private String source;

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }
}
