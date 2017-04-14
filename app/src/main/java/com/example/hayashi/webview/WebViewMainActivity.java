package com.example.hayashi.webview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewMainActivity extends Activity {

    private WebView mWebView = null;
    //コメントYM

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_main);

        // create web view
        mWebView = (WebView)findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String action = intent.getAction();

        //他アプリから呼ばれた時にURLをロードする。
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri!=null) {
                String url = uri.toString();
                mWebView.loadUrl(url);
            }
        }


        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                /*ページの読み込みが始まった時の処理*/
                //サンプル処理　スキーマがENEOSの場合にスキーマ起動＆webviewの破棄
                if(url.startsWith("sb-fido-sample://")){

                    //トークンIDを渡すためスキーマ起動
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);

                    //webviewの破棄
                    mWebView.stopLoading();
                    mWebView.setWebChromeClient(null);
                    mWebView.setWebViewClient(null);
                    mWebView.destroy();
                    mWebView = null;
                }

            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                /*ページの読み込みが終わった時の処理*/
            }

        });
    }
}
