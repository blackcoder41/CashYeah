package com.mogh.cashyeah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import com.mogh.cashyeah.activities.SpeechScreen;
import com.mogh.cashyeah.charts.MultiLineChartActivity;
import com.mogh.cashyeah.models.BranchResponse;
import com.mogh.cashyeah.services.BranchService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnRequest;
    private TextView mTxtResponse;

    private MFPPushNotificationListener notificationListener;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mBtnRequest = (Button) findViewById(R.id.btnRequest);
        mTxtResponse = (TextView) findViewById(R.id.txtResponse);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent speechIntent = new Intent(mContext, SpeechScreen.class);
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

        FirebaseApp.initializeApp(this);

        // Initialize the SDK
        BMSClient.getInstance().initialize(this, BMSClient.REGION_US_SOUTH);
//Initialize client Push SDK

        final MFPPush push = MFPPush.getInstance();
        push.initialize(getApplicationContext(), "7294203d-0806-42a0-984b-43e416f2cead", "69d18c69-01a3-4c55-9dfc-358fa1dc3dbe");

        //Register Android devices
        push.registerDevice(new MFPPushResponseListener<String>()
        {

            @Override
            public void onSuccess(String response)
            {
                //handle successful device registration here
                System.out.println("onSuccess");
//                Toast.makeText(getApplicationContext(), "onSuccess", Toast.LENGTH_SHORT).show();
                push.listen(notificationListener);
            }

            @Override
            public void onFailure(MFPPushException ex)
            {
                //handle failure in device registration here
                System.out.println("onFailure");
//                Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
            }
        });

        // Create notification listener and enable pop up notification when a message is received
        notificationListener = new MFPPushNotificationListener()
        {
            @Override
            public void onReceive(final MFPSimplePushNotification message)
            {
                Log.i(TAG, "Received a Push Notification: " + message.toString());
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
//                        new android.app.AlertDialog.Builder(getApplicationContext())
//                                .setTitle("Received a Push Notification")
//                                .setMessage(message.getAlert())
//                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                    }
//                                })
//                                .show();
                        Toast.makeText(getApplicationContext(), "Received a Push Notification: " + message.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getBranches(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.pnb.com.ph/")
                .build();

        BranchService service = retrofit.create(BranchService.class);



        Call<List<BranchResponse>> call = service.fetchBranchJSON();

        if (true) return;



        call.enqueue(new Callback<List<BranchResponse>>() {
            @Override
            public void onResponse(Call<List<BranchResponse>> call, Response<List<BranchResponse>> response) {

//                List<BranchResponse> branches = response.body();
//                String n = branches.get(0).getData().get(0).getName();
//                mTxtResponse.setText(n);

            }

            @Override
            public void onFailure(Call<List<BranchResponse>> call, Throwable t) {
                Log.d("UHACit", "onFailure");
            }
        });
    }

    public void showLineChart(View view)
    {
        Intent intent = new Intent(MainActivity.this, MultiLineChartActivity.class);
        startActivity(intent);
    }

    public void showSpeech(View view) {

    }
}
