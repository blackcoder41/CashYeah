package com.mogh.cashyeah.charts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.mogh.cashyeah.charts.models.TransactionHistoryController;
import com.mogh.cashyeah.charts.models.TransactionHistoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class MultiLineChartActivity extends ChartBaseActivity
{

    private LineChart mChart;

    private Calendar startCalendar;
    private Calendar endCalendar;

    private Button startDateButton;
    private Button endDateButton;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

    private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
        {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, month);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startDateButton.setText(sdf.format(startCalendar.getTime()));
        }
    };

    private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
        {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, month);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDateButton.setText(sdf.format(endCalendar.getTime()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);

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

        startDateButton = (Button) findViewById(R.id.btn_start_date);
        endDateButton = (Button) findViewById(R.id.btn_end_date);

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.MONTH, 1);

        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawBorders(true);

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
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        mChart.animateX(2000);
        mChart.getXAxis().setLabelCount(10);

        final List<String> labels = new ArrayList<>();
        labels.add("Jan 1");
        labels.add("Jan 30");
        labels.add("Feb 15");
        labels.add("Feb 28");
        labels.add("Mar 15");
        labels.add("Mar 30");
        labels.add("Apr 15");
        labels.add("Apr 30");
        labels.add("May 15");
        labels.add("May 30");

        labels.add("Jun 15");
        labels.add("Jun 30");
        labels.add("Jul 15");
        labels.add("Jul 30");
        labels.add("Aug 15");
        labels.add("Aug 30");
        labels.add("Jan 1");
        labels.add("Jan 1");
        labels.add("Jan 1");
        labels.add("Jan 1");

        labels.add("Jun 15");
        labels.add("Jun 30");
        labels.add("Jul 15");
        labels.add("Jul 30");
        labels.add("Aug 15");
        labels.add("Aug 30");
        labels.add("Jan 1");
        labels.add("Jan 1");
        labels.add("Jan 1");
        labels.add("Jan 1");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.getAxisLeft() .setDrawGridLines(true);
        mChart.getAxisRight() .setDrawGridLines(true);

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
//        getMenuInflater().inflate(R.menu.line, menu);
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

    //    @Override
    public void onProgressChanged()
    {

        mChart.resetTracking();

//        tvX.setText("" + (mSeekBarX.getProgress()));
//        tvY.setText("" + (mSeekBarY.getProgress()));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        List<TransactionHistoryItem> transactionHistoryItems = new TransactionHistoryController().getTransactionHistory();

        for (int z = 0; z < 3; z++)
        {

            ArrayList<Entry> values = new ArrayList<Entry>();

//            for (int i = 0; i < mSeekBarX.getProgress(); i++) {
//                double val = (Math.random() * mSeekBarY.getProgress()) + 3;
//                values.add(new Entry(i, (float) val));
//            }

            int index = 0;
            Iterator<TransactionHistoryItem> iter = transactionHistoryItems.iterator();
            while (iter.hasNext())
            {
                TransactionHistoryItem item = iter.next();
                values.add(new Entry(index, Float.valueOf(item.getAmount())));
                index += 1;
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(5f);
            d.setCircleRadius(0f);

            int color = mColors[z % mColors.length];
            if (z == 2)
            {
                d.setColor(Color.rgb(0, 0, 255));
                d.setCircleColor(Color.rgb(0, 0, 255));
            } else if (z == 1)
            {
                d.setColor(Color.RED);
                d.setCircleColor(Color.RED);
            }

            dataSets.add(d);
        }

        LineData data = new LineData(dataSets);
        data.removeDataSet(0);
        mChart.setData(data);
        mChart.invalidate();
    }
}
