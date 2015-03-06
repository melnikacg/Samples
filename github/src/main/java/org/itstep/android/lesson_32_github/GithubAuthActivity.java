package org.itstep.android.lesson_32_github;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/*
 * GithubAuthActivity.java
 *
 * Created by mib on 18.02.15, 19:02
 *
 * Copyright(c) 2015 Provectus IT Company, Inc.  All Rights Reserved.
 * This software is the proprietary information of Provectus IT Company.
 *
 */
public class GithubAuthActivity extends ActionBarActivity {

    private static final String TAG = GithubAuthActivity.class.getSimpleName();

    public static final String EXTRA_KEY_ACCESS_TOKEN = "EXTRA_KEY_ACCESS_TOKEN";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_auth);

        final WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {
                Log.i(TAG, "Url: " + url);
                if (url.startsWith(Constants.AUTH_REDIRECT_URL)) {
                    final String code = Uri.parse(url).getQueryParameter("code");
                    Log.i(TAG, "Code: " + code);

                    sendTokenRequest(code);

                    return true;
                }

                return false;
            }
        };

        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.loadUrl(Constants.getAuthUrl());
    }

    private void sendTokenRequest(final String code) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new AccessTokenRequest(code, listener, errorListener));
    }

    final Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(final String response) {
            Log.i(TAG, "Response: " + response);

            final String accessToken = Uri.parse("http://localhost?" + response)
                                          .getQueryParameter("access_token");
            Log.i(TAG, "Access token: " + accessToken);

            final Intent intent = new Intent();
            intent.putExtra(EXTRA_KEY_ACCESS_TOKEN, accessToken);

            setResult(RESULT_OK, intent);
            finish();
        }
    };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(final VolleyError error) {
            Log.e(TAG, "Error", error);

            finish();
        }
    };
}