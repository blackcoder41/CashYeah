package com.mogh.cashyeah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mogh.cashyeah.charts.models.TransactionHistoryItem;
import com.mogh.cashyeah.services.connection.services.TransactionHistoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Christian on 12/2/2017.
 */

public class TxnHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txn_history);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTransactionHistory();
    }

    public void getTransactionHistory() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cherrypaperdesigns.com/bank_api/")
                .build();

        TransactionHistoryService service = retrofit.create(TransactionHistoryService.class);

        Call<List<TransactionHistoryItem>> call = service.getTransactionHistory();

        call.enqueue(new Callback<List<TransactionHistoryItem>>() {

            @Override
            public void onResponse(Call<List<TransactionHistoryItem>> call, Response<List<TransactionHistoryItem>> response) {

                List<TransactionHistoryItem> txnHis = response.body();
                String txnHistory =  txnHis.get(0).getClientId();



            }

            @Override
            public void onFailure(Call<List<TransactionHistoryItem>> call, Throwable t) {

            }
        });
    }
}