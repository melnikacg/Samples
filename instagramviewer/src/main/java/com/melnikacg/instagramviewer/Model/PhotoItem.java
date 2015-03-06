package com.melnikacg.instagramviewer.Model;

import com.google.gson.annotations.SerializedName;
import com.melnikacg.instagramviewer.Presenter.RelativeTime;

import java.util.ArrayList;

public class PhotoItem {

    @SerializedName("created_time")
    private String mCreatedTime;

    @SerializedName("id")
    private String id;
    @SerializedName("user")
    private PhotoItemUser mPhotoItemUser;
    @SerializedName("images")
    private PhotoItemImages mPhotoItemImages;
    @SerializedName("caption")
    private PhotoItemCaption mPhotoItemCaption;
    @SerializedName("likes")
    private PhotoItemLikes mPhotoItemLikes;
    @SerializedName("comments")
    private PhotoItemComments mPhotoItemComments;

    public String getId() {
        return id;
    }

    // non-fields
    public String getImageUrl() {
        if (mPhotoItemImages != null && mPhotoItemImages.mPhotoItemImagesStandartResolution != null)
            return mPhotoItemImages.mPhotoItemImagesStandartResolution.getImageUrl();
        return null;
    }

    //  non-fields
    public String getProfileUrl() {

        if (mPhotoItemUser != null)
            return mPhotoItemUser.getProfileUrl();
        return null;
    }

    // non-fields
    public String getUserName() {

        if (mPhotoItemUser != null)
            return mPhotoItemUser.getUsername();
        return null;
    }

    // non-fields
    public String getCaption() {

        if (mPhotoItemCaption != null)
            return mPhotoItemCaption.getText();
        return null;
    }

    // non-fields
    public int getLikesCount() {

        if (mPhotoItemLikes != null)
            return mPhotoItemLikes.getLikesCount();
        return 0;
    }

    // non-fields
    public int getCommentsCount() {

        if (mPhotoItemComments != null)
            return mPhotoItemComments.getCount();
        return 0;
    }

    // non-fields
    public ArrayList<CommentItem> getCommentsArrayList() {

        if (getCommentsCount() > 0)
            return mPhotoItemComments.getCommentItems();
        return null;
    }

    //non-field
    public String getRelativeTime() {

        return RelativeTime.getRelativeTime(mCreatedTime);
    }

    static class PhotoItemCaption {

        @SerializedName("text")
        private String mText;

        public String getText() {
            return mText;
        }
    }

    static class PhotoItemImages {

        @SerializedName("images")
        private String mImages;

        @SerializedName("standard_resolution")
        private PhotoItemImagesStandartResolution mPhotoItemImagesStandartResolution;

        static class PhotoItemImagesStandartResolution {

            @SerializedName("url")
            public String mImageUrl;

            public String getImageUrl() {
                return mImageUrl;
            }

        }

    }

    static class PhotoItemLikes {

        @SerializedName("count")
        private int mLikesCount;

        public int getLikesCount() {
            return mLikesCount;
        }
    }


    static class PhotoItemUser {

        @SerializedName("username")
        private String mUsername;

        @SerializedName("profile_picture")
        private String mProfileUrl;

        public String getProfileUrl() {
            return mProfileUrl;
        }

        public String getUsername() {
            return mUsername;
        }

    }

    static class PhotoItemComments {

        @SerializedName("count")
        private int mCount;

        @SerializedName("data")
        private ArrayList<CommentItem> mCommentItems;

        public ArrayList<CommentItem> getCommentItems() {
            return mCommentItems;
        }

        public int getCount() {
            return mCount;
        }
    }
}
