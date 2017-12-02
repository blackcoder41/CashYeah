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

public class LoginScreen extends AppCompatActivity {

    private Context mContext;
    private WebView mWebView;


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