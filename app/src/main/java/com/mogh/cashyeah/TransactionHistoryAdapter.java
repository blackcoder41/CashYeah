package com.mogh.cashyeah;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mogh on 12/3/2017.
 */

public class TransactionHistoryAdapter extends ArrayAdapter<String> {

    Context mContext;
    int mResource;
    int mTextViewResourceId;

    List<String> mAmounts;

    public TransactionHistoryAdapter(@NonNull Context context, @LayoutRes int resource,
                                     @IdRes int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        String amount = mAmounts.get(position);
        TextView rowAmount = (TextView) view.findViewById(mTextViewResourceId);
        rowAmount.setText(amount);

        return view;

    }
}
