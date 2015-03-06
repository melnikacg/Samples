package org.itstep.android.lesson_32_github;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
 * AccessTokenRequest.java
 *
 * Created by mib on 18.02.15, 19:55
 *
 * Copyright(c) 2015 Provectus IT Company, Inc.  All Rights Reserved.
 * This software is the proprietary information of Provectus IT Company.
 *
 */
public class AccessTokenRequest extends StringRequest {

    private final String mCode;

    public AccessTokenRequest(final String code,
                              final Response.Listener<String> listener,
                              final Response.ErrorListener errorListener) {
        super(Method.POST, Constants.TOKEN_URL, listener, errorListener);

        mCode = code;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        final Map<String, String> params = new HashMap<>();
        params.put("code", mCode);
        params.put("client_id", Constants.CLIENT_ID);
        params.put("client_secret", Constants.CLIENT_SECRET);
        params.put("redirect_url", Constants.TOKEN_REDIRECT_URL);
        return params;
    }
}