package com.melnikacg.instagramviewer.Model;

import com.google.gson.annotations.SerializedName;
import com.melnikacg.instagramviewer.Presenter.RelativeTime;

public class PhotoItem {

    @SerializedName("created_time")
    private String mCreatedTime;

    @SerializedName("id")
    private String id;

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


    // TODO
    public String getComment1() {
        return "";
    }

    public String getUser1() {
        return "";
    }

    public String getComment2() {
        return "";
    }

    public String getUser2() {
        return "";
    }

    //public int imageHeight;
    //public int likesCount;
    //public int commentsCount;

    //non-field
    public String getRelativeTime() {

        return RelativeTime.getRelativeTime(mCreatedTime);
    }

    class PhotoItemCaption {

        //photo.caption = photoJSON.getJSONObject("caption").getString("text");

        @SerializedName("text")
        private String mText;

        public String getText() {
            return mText;
        }
    }


    class PhotoItemImages {
        // username, caption, image_url, height, likes_count

        @SerializedName("images")
        private String mImages;

        @SerializedName("standard_resolution")
        private PhotoItemImagesStandartResolution mPhotoItemImagesStandartResolution;

        //public String getImages() {
        //    return mImages;
        //}

        // photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        //photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");

        class PhotoItemImagesStandartResolution {
            // username, caption, image_url, height, likes_count

            @SerializedName("url")
            public String mImageUrl;

            @SerializedName("height")
            public int mImageHeight;

            public String getImageUrl() {
                return mImageUrl;
            }

            public int getImageHeight() {
                return mImageHeight;
            }

            // photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            //photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
        }

    }

    class PhotoItemLikes {

        //photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

        @SerializedName("count")
        private int mLikesCount;


        public int getLikesCount() {
            return mLikesCount;
        }
    }


    class PhotoItemUser {

        // photo.profileUrl = photoJSON.getJSONObject("user").getString("profile_picture");
        // photo.username = photoJSON.getJSONObject("user").getString("username");

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

    class PhotoItemComments {

        //photo.commentsCount = photoJSON.getJSONObject("comments").getInt("count")

        @SerializedName("count")
        private int mCount;

        public int getCount() {
            return mCount;
        }
    }
}
