package org.itstep.android.lesson_32_github;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/*
 * GitHubRepo.java
 *
 * Created by mib on 18.02.15, 20:54
 *
 * Copyright(c) 2015 Provectus IT Company, Inc.  All Rights Reserved.
 * This software is the proprietary information of Provectus IT Company.
 *
 */
public class GitHubRepo {

    @SerializedName("name")
    private String mName;

    @SerializedName("created_at")
    private String mCreatedAt;

    @SerializedName("language")
    private String mLanguage;

    public String getName() {
        return mName;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public static class List extends ArrayList<GitHubRepo>{}
}
