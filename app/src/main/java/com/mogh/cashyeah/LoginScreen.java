package com.mogh.cashyeah;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = LoginScreen.class.getSimpleName();
    private Context mContext;
    private WebView mWebView;

    private MFPPushNotificationListener notificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mContext = this;

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebViewJS(this), "android");


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.indexOf("access_token") >= 0 ) {

                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                if (url.indexOf("orgid") >= 0 ) {

                    mWebView.loadUrl(getString(R.string.script_credentials_pmelemos));

                    Log.d("DEBUG_TAG", url);
                    Log.d("DEBUG_TAG", getString(R.string.script_credentials_pmelemos));

                }
            }


        });

        mWebView.loadUrl( getString(R.string.unionbank_login_uri) );

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

    public class WebViewJS {
        Context mContext;


        public WebViewJS(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void loginRedirect () {


        }
    }
}