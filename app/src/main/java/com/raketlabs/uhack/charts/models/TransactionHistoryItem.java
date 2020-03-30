package com.raketlabs.uhack.charts.models;

import java.util.Date;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class TransactionHistoryItem {
    private int id;
    private Date date;
    private String amount;
    private String clientId;
    private String type;
    private String description;

    public TransactionHistoryItem(int id, Date date, String amount, String clientId, String type, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.clientId = clientId;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
