package com.raketlabs.uhack.charts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.raketlabs.uhack.R;
import com.raketlabs.uhack.TransactionHistoryScreen;
import com.raketlabs.uhack.activities.SpeechScreen;
import com.raketlabs.uhack.charts.models.TransactionHistoryController;
import com.raketlabs.uhack.charts.models.TransactionHistoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

//import com.raketlabs.uhack.services.connection.controller.TransactionHistoryController;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class MultiLineChartActivity extends ChartBaseActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private LineChart mChart;

    private Calendar startCalendar;
    private Calendar endCalendar;

    private ImageView startDateButton;
    private ImageView endDateButton;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

    private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
        {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, month);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
        {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, month);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_chart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent speechIntent = new Intent(getApplicationContext(), SpeechScreen.class);
                startActivity(speechIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mChart = (LineChart) findViewById(R.id.chart1);

        mChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showTransactionHistory();
            }
        });

        startDateButton = (ImageView) findViewById(R.id.btn_start_date);
        endDateButton = (ImageView) findViewById(R.id.btn_end_date);

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.MONTH, 1);

        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawBorders(true);
        mChart.setBorderColor(Color.LTGRAY);

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        Legend l = mChart.getLegend();

        mChart.animateX(2000);
        mChart.getXAxis().setLabelCount(10);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.getAxisLeft().setDrawGridLines(true);
        mChart.getAxisRight().setDrawGridLines(true);

        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                return mMonths[(int) value % mMonths.length];
            }
        });

        onProgressChanged();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sendNotification("Watch out! Your current balance is running low.");
    }

    private void sendNotification(String messageBody)
    {
        Intent intent = new Intent(this, MultiLineChartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.mipmap.ic_launcher_round);

        Bitmap bigSale = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.big_sale);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bigSale))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void startDateButtonClicked(View view)
    {
        showDialog(998);
    }

    public void endDateButtonClicked(View view)
    {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        // TODO Auto-generated method stub
        if (id == 998)
        {
            return new DatePickerDialog(this,
                    startDateListener, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
        } else if (id == 999)
        {
            return new DatePickerDialog(this,
                    endDateListener, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return true;
    }

    private int[] mColors = new int[]{
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    public void onProgressChanged()
    {

        mChart.resetTracking();

        final ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        List<TransactionHistoryItem> transactionHistoryItems = new TransactionHistoryController().getTransactionHistoryDeposit();
        List<TransactionHistoryItem> transactionHistoryItemsWithdrawals = new TransactionHistoryController().getTransactionHistoryWithdrawal();
        displayChart(dataSets, transactionHistoryItems, transactionHistoryItemsWithdrawals);
    }

    private void displayChart(ArrayList<ILineDataSet> dataSets, List<TransactionHistoryItem> transactionHistoryItemsDeposits, List<TransactionHistoryItem> transactionHistoryItemsWithdrawals)
    {
        LineDataSet lineDataSetDeposits = setTransactionItems(dataSets, transactionHistoryItemsDeposits, "Deposits", Color.rgb(0, 0, 255));
        LineDataSet lineDataSetWithdrawals = setTransactionItems(dataSets, transactionHistoryItemsWithdrawals, "Withdrawals", Color.RED);

        dataSets.add(lineDataSetDeposits);
        dataSets.add(lineDataSetWithdrawals);

        LineData data = new LineData(dataSets);
        mChart.setData(data);
        mChart.invalidate();
    }

    private LineDataSet setTransactionItems(ArrayList<ILineDataSet> dataSets, List<TransactionHistoryItem> transactionHistoryItemsDeposits, String legendName, int color)
    {
        ArrayList<Entry> values = new ArrayList<Entry>();

        int index = 0;
        Iterator<TransactionHistoryItem> iter = transactionHistoryItemsDeposits.iterator();
        while (iter.hasNext())
        {
            TransactionHistoryItem item = iter.next();
            values.add(new Entry(index, Float.valueOf(item.getAmount())));
            index += 1;
        }

        LineDataSet d = new LineDataSet(values, legendName);
        d.setLineWidth(5f);
        d.setCircleRadius(0f);

        d.setColor(color);
        d.setCircleColor(color);

        return d;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        return false;
    }

    public void showTransactionHistory()
    {

        Intent txtHistory = new Intent(this, TransactionHistoryScreen.class);
        startActivity(txtHistory);
    }
}
