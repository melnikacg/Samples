package org.itstep.android.lesson_32_github;

import com.google.gson.annotations.SerializedName;

/*
 * User.java
 *
 * Created by mib on 18.02.15, 20:20
 *
 * Copyright(c) 2015 Provectus IT Company, Inc.  All Rights Reserved.
 * This software is the proprietary information of Provectus IT Company.
 *
 */
public class GitHubUser {

    @SerializedName("login")
    private String mLogin;

    @SerializedName("avatar_url")
    private String mAvatarUrl;

    public String getLogin() {
        return mLogin;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }
}