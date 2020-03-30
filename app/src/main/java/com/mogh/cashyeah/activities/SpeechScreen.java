package com.mogh.cashyeah.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mogh.cashyeah.LoginScreen;
import com.mogh.cashyeah.R;
import com.mogh.cashyeah.Speaker;
import com.mogh.cashyeah.charts.MultiLineChartActivity;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class SpeechScreen extends AppCompatActivity {


    private Context mContext;

    private WebView mWebView;


    private final int CHECK_CODE = 0x1;
    private final int LONG_DURATION = 5000;
    private final int SHORT_DURATION = 1200;

    Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_screen);

        checkTTS();

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new SpeechScreen.WebViewJS(this), "android");



        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }




        });


    }

    public void talk_back(View view) {

        saySomething("Hey! How may I help you?");

    }


    public class WebViewJS {
        Context mContext;


        public WebViewJS(Context c)
        {
            mContext = c;
        }

        @JavascriptInterface public void speak(String strSpeech) {

            SpeechScreen.this.saySomething(strSpeech);
        }
    }


    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                speaker = new Speaker(this);
            }else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }

        mWebView.loadUrl("file:///android_asset/forex.html");
    }

    public void saySomething(String speech) {



        speaker.allow(true);
        speaker.speak(speech);

        Toast toast = Toast.makeText(this, speech, Toast.LENGTH_SHORT);
        toast.show();
    }
}
