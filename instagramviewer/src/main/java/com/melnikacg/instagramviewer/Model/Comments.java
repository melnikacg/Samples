package com.melnikacg.instagramviewer.Model;

import com.google.gson.annotations.SerializedName;
import com.melnikacg.instagramviewer.Presenter.RelativeTime;

import java.util.ArrayList;

public class Comments {

    @SerializedName("data")
    private ArrayList<CommentItem> mCommentItems;

    public ArrayList<CommentItem> getCommentItems() {
        return mCommentItems;
    }

}
