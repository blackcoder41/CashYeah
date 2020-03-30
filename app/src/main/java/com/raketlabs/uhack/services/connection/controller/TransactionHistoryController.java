package com.raketlabs.uhack.services.connection.controller;

import android.util.Log;

import com.raketlabs.uhack.charts.models.TransactionHistoryItem;
import com.raketlabs.uhack.services.connection.TokenSessionCallback;
import com.raketlabs.uhack.services.connection.models.TransactionHistoryRequest;
import com.raketlabs.uhack.services.connection.services.TransactionHistoryService;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class TransactionHistoryController extends BaseController<TransactionHistoryService>
{
    public static final String TAG = TransactionHistoryController.class.getSimpleName();

    public interface GetTransactionHistoryCallback extends TokenSessionCallback
    {
        void getTransactionHistorySuccessful(LinkedList<TransactionHistoryItem> transactionHistoryItems);

        void getTransactionHistoryFailed();
    }

    public void getTransactionHistory(String tokenId, final GetTransactionHistoryCallback getAnnouncementsCallback)
    {
        final TransactionHistoryService transactionHistoryService = createService(TransactionHistoryService.class);

        TransactionHistoryRequest request = new TransactionHistoryRequest();
        request.setTokenId(tokenId);

        final Call<List<TransactionHistoryItem>> call =
                transactionHistoryService.getTransactionHistory();

        call.enqueue(new Callback<List<TransactionHistoryItem>>()
        {
            @Override
            public void onResponse(Call<List<TransactionHistoryItem>> call, Response<List<TransactionHistoryItem>> response)
            {
                Log.d(TAG, "transaction history response: " + response.body());
            }

            @Override
            public void onFailure(Call<List<TransactionHistoryItem>> call, Throwable t)
            {
                Log.d(TAG, "transaction history response failed: " + t.getMessage());
                getAnnouncementsCallback.getTransactionHistoryFailed();
            }
        });
    }
}