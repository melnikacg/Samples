package com.melnikacg.instagrammaterial.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Comments {

    @SerializedName("data")
    private ArrayList<CommentItem> mCommentItems;

    public ArrayList<CommentItem> getCommentItems() {
        return mCommentItems;
    }

}
