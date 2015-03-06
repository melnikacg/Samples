package org.itstep.android.lesson_32_github;

import android.net.Uri;

/*
 * Constants.java
 *
 * Created by mib on 18.02.15, 18:52
 *
 * Copyright(c) 2015 Provectus IT Company, Inc.  All Rights Reserved.
 * This software is the proprietary information of Provectus IT Company.
 *
 */
public class Constants {

    private Constants() {
        // No instance
    }

    public static final String AUTH_REDIRECT_URL = "http://localhost/auth/success";
    public static final String TOKEN_REDIRECT_URL = "http://localhost/token/success";

    public static final String CLIENT_ID = "37a9c02cf00c2c3f1abd";
    public static final String CLIENT_SECRET =
            "6d085f67e08b5965a402d9a3dc6d18d32bcb2905";


    private static final String AUTH_BASE_URL = "https://github.com/login/oauth";
    private static final String AUTH_URL = AUTH_BASE_URL + "/authorize";
    public static final String TOKEN_URL = AUTH_BASE_URL + "/access_token";

    public static final String BASE_URL = "https://api.github.com";

    public static String getAuthUrl() {
        final String scope = "user";

        return Uri.parse(AUTH_URL)
           .buildUpon()
           .appendQueryParameter("client_id", CLIENT_ID)
           .appendQueryParameter("redirect_uri", AUTH_REDIRECT_URL)
           .appendQueryParameter("scope", scope)
           .appendQueryParameter("state", "rnd")
           .build().toString();
    }
}