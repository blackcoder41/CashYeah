package com.mogh.cashyeah;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by mogh on 12/3/2017.
 */

public class TransactionHistoryAdapter extends ArrayAdapter<String> {

    Context mContext;
    int mResource;
    int mImgViewResourceId;
    List<String> mImageURLs;


    public TransactionHistoryAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
