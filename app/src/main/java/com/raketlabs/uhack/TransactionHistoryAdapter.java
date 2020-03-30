package com.raketlabs.uhack;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by raketlabs on 12/3/2017.
 */

public class TransactionHistoryAdapter extends BaseAdapter {

    Context mContext;
    int mResource;
    int mTextViewResourceId;

    LayoutInflater mInflater;

    ArrayList<HashMap<Integer, String>> mAmounts;

    TransactionHistoryAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull ArrayList<HashMap<Integer, String>> amounts) {

        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mResource = resource;
        this.mAmounts = amounts;

    }

    @Override
    public int getCount() {
        return mAmounts.size();
    }

    @Override
    public Object getItem(int i) {
        return mAmounts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        if (view != null) {
            TextView txtDescription = (TextView) view.findViewById(R.id.row_description);
            TextView txtDate = (TextView) view.findViewById(R.id.row_date);
            TextView txtAmount = (TextView) view.findViewById(R.id.row_amount);

            HashMap<Integer, String> row = mAmounts.get(position);

            txtDescription.setText(row.get(TransactionJSON.DESCRIPTION));
            txtDate.setText(row.get(TransactionJSON.DATE));
            txtAmount.setText(row.get(TransactionJSON.AMOUNT));


        } else {
            Log.d("bnm", "view: " + view);
        }

        return view;
    }
}
