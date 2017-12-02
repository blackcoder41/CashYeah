package com.mogh.cashyeah.charts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.mogh.cashyeah.R;
import com.mogh.cashyeah.activities.SpeechScreen;
import com.mogh.cashyeah.charts.models.TransactionHistoryController;
import com.mogh.cashyeah.charts.models.TransactionHistoryItem;
//import com.mogh.cashyeah.services.connection.controller.TransactionHistoryController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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
//            startDateButton.setText(sdf.format(startCalendar.getTime()));
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
//            endDateButton.setText(sdf.format(endCalendar.getTime()));
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

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
                // TODO transition to traditional transaction history
                Toast.makeText(getApplicationContext(), "transition to traditional transaction history", Toast.LENGTH_SHORT).show();
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

//        new TransactionHistoryController().getTransactionHistoryDeposit("", new TransactionHistoryController.GetTransactionHistoryCallback()
//        {
//            @Override
//            public void getTransactionHistorySuccessful(LinkedList<TransactionHistoryItem> transactionHistoryItems)
//            {
//        displayChart(dataSets, transactionHistoryItems);
//            }
//
//            @Override
//            public void getTransactionHistoryFailed()
//            {
//
//            }
//
//            @Override
//            public void tokenSessionInvalid(String message)
//            {
//
//            }
//        });
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
}
