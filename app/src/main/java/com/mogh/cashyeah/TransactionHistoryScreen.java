package com.mogh.cashyeah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class TransactionHistoryScreen extends AppCompatActivity {

    ListView mListView;
    ArrayList<String> mBalances;
    TransactionHistoryAdapter mAdapter;

    final String BASE_URL = "http://cherrypaperdesigns.com/";
    final String END_POINT = "bank_api/tran_history/?access_token=1";
    final String TRAN_SERVICE_END_POINT = "http://cherrypaperdesigns.com/bank_api/tran_history/?access_token=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_screen);

        mListView = (ListView) findViewById(R.id.listTxnHistory);

        mBalances = new ArrayList<>();
        mAdapter = new TransactionHistoryAdapter(this, R.layout.row_transaction, R.id.row_amount, mBalances);
        mListView.setAdapter(mAdapter);


        // Initialize Volley HTTP library
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        // Create a Volley HTTP Request
        JsonArrayRequest jsonEvents = new JsonArrayRequest(Request.Method.GET, TRAN_SERVICE_END_POINT, null,

                // This method is called after successfully downloading the json
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Get the length of json and display in a toast
                        String msg = "There are " + response.length() + " records.";
                        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                        toast.show();

                        if (true) return;

                        // Go through the json array
                        for (int i=0; i<response.length(); i++) {

                            JSONObject item = null;
                            try {
                                // Get one row
                                item = (JSONObject) response.get(i);
                                String strAmount = item.get("amount").toString();

                                // Add image to listview
                                TransactionHistoryScreen.this.mBalances.add( strAmount );

                                // !important update the listview
                                BaseAdapter baseAdapter = (BaseAdapter) TransactionHistoryScreen.this.mListView.getAdapter();
                                baseAdapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                                Log.d("json1", "Error in reading JSON. " + e.getMessage());
                            }

                        }


                    }
                },

                // This is called if there is an error like json format, internet connection, other errors
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String msg = "Encountered an error. " + error.getMessage();
                        Toast eventCountPrompt = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                        eventCountPrompt.show();


                    }
                });


        // Don't forget to add the request to the queue
        requestQueue.add(jsonEvents);
    }


}
