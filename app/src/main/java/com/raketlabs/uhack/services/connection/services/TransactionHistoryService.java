package com.raketlabs.uhack.services.connection.services;

import com.raketlabs.uhack.charts.models.TransactionHistoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public interface TransactionHistoryService
{
    @GET("/transactions/history")
    Call<List<TransactionHistoryItem>> getTransactionHistory();
}

