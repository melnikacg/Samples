package com.melnikacg.instagramviewer.Model;

public class Constants {

    private Constants() {
        // No instance
    }

    // InstagramAPI

    //public static final String AUTH_REDIRECT_URL = "http://localhost/auth/success";
    //public static final String TOKEN_REDIRECT_URL = "http://localhost/token/success";

    public static final String CLIENT_ID = "c8e2cde3f35d402687512d9004ee7b12";

    //public static final String CLIENT_SECRET = "6d085f67e08b5965a402d9a3dc6d18d32bcb2905";

    //public static final String POPULAR_URL = "https://api.instagram.com/v1/media/popular?client_id=";

    public static final String MEDIA_URL = "https://api.instagram.com/v1/media/";
    public static final String POPULAR_URL = MEDIA_URL + "popular?client_id=";

    public static final String COMMENTS_CLIENT_URL = "/comments?client_id=";

    //private static final String AUTH_BASE_URL = "https://github.com/login/oauth";
    //private static final String AUTH_URL = AUTH_BASE_URL + "/authorize";
    //public static final String TOKEN_URL = AUTH_BASE_URL + "/access_token";

    //public static final String BASE_URL = "https://api.github.com";

    // LogCat
    public static final String ROBO_SPICE_LOG_TAG = "SampleSpiceService";

}
