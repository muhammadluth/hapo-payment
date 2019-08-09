package com.pulsa.webView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pulsa.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class akunWebview extends AppCompatActivity {
    String mURL = "";
    ProgressBar pb;
    TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Panduan Aplikasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.detail_view);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        loading = (TextView) findViewById(R.id.loading);

        if (getIntent().getStringExtra("title") != null) {
            setTitle(getIntent().getStringExtra("title"));
        }

        mURL = getIntent().getStringExtra("url");
        String aksi = getIntent().getStringExtra("aksi");
        if (!mURL.trim().equalsIgnoreCase("")) {
            WebView myWebView = (WebView) findViewById(R.id.pageInfo);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new MyWebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    pb.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    pb.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                }
            });
            if (aksi != null) {
                try {
                    String postData = "id=" + URLEncoder.encode(aksi, "UTF-8");
                    myWebView.postUrl(mURL, postData.getBytes());
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            } else {
                myWebView.loadUrl(mURL.trim());
            }
        }

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            return true;
        }

        public void onReceivedError(WebView webview, int i, String s, String s1) {
            webview.loadUrl("file:///android_asset/error.html");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
