package com.melnikacg.instagramviewer.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Photos {

    @SerializedName("data")
    private ArrayList<PhotoItem> mPhotoItems;

    public ArrayList<PhotoItem> getPhotoItems() {
        return mPhotoItems;
    }
}
