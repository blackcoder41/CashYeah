package com.mogh.cashyeah.services.con.models;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class APIResponse
{
    private int statusCode;
    private String statusDesc;
    private String transactionId;
    private String transactionDate;
    private String clientRequestID;

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getStatusDesc()
    {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc)
    {
        this.statusDesc = statusDesc;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate)
    {
        this.transactionDate = transactionDate;
    }

    public String getClientRequestID()
    {
        return clientRequestID;
    }

    public void setClientRequestID(String clientRequestID)
    {
        this.clientRequestID = clientRequestID;
    }
}