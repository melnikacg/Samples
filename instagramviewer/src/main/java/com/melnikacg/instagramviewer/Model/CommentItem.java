package com.melnikacg.instagramviewer.Model;

import com.google.gson.annotations.SerializedName;
import com.melnikacg.instagramviewer.Presenter.RelativeTime;

public class CommentItem {

    @SerializedName("text")
    private String mText;

    @SerializedName("created_time")
    private String mCreatedTime;

    @SerializedName("from")
    private CommentItemFrom mCommentItemFrom;

    public String getText() {
        return mText;
    }

    //non-fields
    public String getProfileUrl() {
        if (mCommentItemFrom != null)
            return mCommentItemFrom.getProfileUrl();
        return null;
    }

    //non-fields
    public String getUsername() {
        if (mCommentItemFrom != null)
            return mCommentItemFrom.getUsername();
        return null;
    }

    //non-fields
    public String getRelativeTime() {
        return RelativeTime.getRelativeTime(mCreatedTime);
    }

    static class CommentItemFrom {

        @SerializedName("profile_picture")
        private String mProfileUrl;

        @SerializedName("username")
        private String mUsername;

        public String getProfileUrl() {
            return mProfileUrl;
        }

        public String getUsername() {
            return mUsername;
        }
    }
}
