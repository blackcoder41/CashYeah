package com.mogh.cashyeah;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.FirebaseApp;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import com.mogh.cashyeah.charts.MultiLineChartActivity;

public class LoginScreen extends AppCompatActivity
{

    private static final String TAG = LoginScreen.class.getSimpleName();
    private Context mContext;
    private WebView mWebView;

    private int mLoginPortalLoaded;

    private MFPPushNotificationListener notificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        mContext = this;

        mLoginPortalLoaded = 0;

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebViewJS(this), "android");



        mWebView.setWebViewClient(new WebViewClient()
        {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);

                Log.d("DEBUG_TAG", url);

                if (mLoginPortalLoaded < 1)
                {
                    mWebView.setVisibility(View.VISIBLE);
                }

                if (url.indexOf("access_token") >= 0)
                {
                    Intent intent = new Intent(mContext, MultiLineChartActivity.class);
                    startActivity(intent);
                    finish();
                }

                if (url.indexOf("orgid") >= 0)
                {
                    mWebView.loadUrl(getString(R.string.script_credentials_oralhernandez));

                    Log.d("DEBUG_TAG", url);
                    Log.d("DEBUG_TAG", getString(R.string.script_credentials_oralhernandez));
                }

                mLoginPortalLoaded++;
            }
        });

        mWebView.loadUrl(getString(R.string.unionbank_login_uri));
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
                push.listen(notificationListener);
            }

            @Override
            public void onFailure(MFPPushException ex)
            {
                System.out.println("onFailure");
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
                        Context context = getApplicationContext();
                        NotificationManager notificationManager = (NotificationManager) context
                                .getSystemService(NOTIFICATION_SERVICE);

                        Notification updateComplete = new Notification();
                        updateComplete.icon = android.R.drawable.stat_notify_sync;
                        updateComplete.tickerText = message.getAlert();
                        updateComplete.when = System.currentTimeMillis();

                        Intent notificationIntent = new Intent(context,
                                MultiLineChartActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                                notificationIntent, 0);

                        String contentTitle = message.getAlert();
                        Notification.Builder builder = new Notification.Builder(getApplicationContext());

                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(contentTitle)
                                .setContentIntent(contentIntent);

                        Notification notification = builder.getNotification();
                        notificationManager.notify(R.mipmap.ic_launcher, notification);
                    }
                });
            }
        };
    }

    public class WebViewJS
    {
        Context mContext;

        public WebViewJS(Context c)
        {
            mContext = c;
        }

        @JavascriptInterface
        public void loginRedirect()
        {
            // do nothing
        }
    }
}