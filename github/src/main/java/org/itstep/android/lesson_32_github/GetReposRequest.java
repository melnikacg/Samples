package org.itstep.android.lesson_32_github;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/*
 * GitHubUserRequest.java
 *
 * Created by mib on 18.02.15, 20:23
 *
 * Copyright(c) 2015 Provectus IT Company, Inc.  All Rights Reserved.
 * This software is the proprietary information of Provectus IT Company.
 *
 */
public class GetReposRequest extends Request<GitHubRepos> {

    private final String mAccessToken;
    private final Response.Listener<GitHubRepos> mListener;

    public GetReposRequest(final String accessToken, final Response.Listener<GitHubRepos> listener, final Response.ErrorListener errorListener) {
        super(Method.GET, Constants.BASE_URL + "/user/repos", errorListener);

        mAccessToken = accessToken;
        mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("token %s", mAccessToken));
        return headers;
    }

    @Override
    protected Response<GitHubRepos> parseNetworkResponse(final NetworkResponse response) {
        final Gson gson = new Gson();

        String json;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }

        final GitHubRepos gitHubUser = gson.fromJson(json, GitHubRepos.class);

        return Response.success(gitHubUser, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(final GitHubRepos response) {
        mListener.onResponse(response);
    }
}
