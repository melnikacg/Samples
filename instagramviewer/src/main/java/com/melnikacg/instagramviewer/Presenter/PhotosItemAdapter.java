package com.melnikacg.instagramviewer.Presenter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnikacg.instagramviewer.Model.CommentItem;
import com.melnikacg.instagramviewer.Model.PhotoItem;
import com.melnikacg.instagramviewer.R;
import com.melnikacg.instagramviewer.View.CommentsActivity;
import com.melnikacg.instagramviewer.View.ExImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotosItemAdapter extends ArrayAdapter<PhotoItem> {

    public PhotosItemAdapter(Context context, List<PhotoItem> photos) {
        super(context, android.R.layout.simple_list_item_1, photos);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Take the data source at position (e.g. 0)
        // Get the data item

        final ViewHolder viewHolder;

        // Check if we are using a recycled view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

            viewHolder = new ViewHolder();
            // Lookup the subview within the template
            viewHolder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
            viewHolder.imgPhoto = (ExImageView) convertView.findViewById(R.id.imgPhoto);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvViewAllComments = (TextView) convertView.findViewById(R.id.tvViewAllComments);
            viewHolder.tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
            viewHolder.tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        PhotoItem photoItem = getItem(position);


        viewHolder.tvUsername.setText(photoItem.getUserName());
        viewHolder.tvTime.setText(photoItem.getRelativeTime());
        viewHolder.tvLikes.setText(String.format("%d likes", photoItem.getLikesCount()));

        if (photoItem.getCaption() != null)
            setCaption(viewHolder.tvCaption, photoItem);
        else
            viewHolder.tvCaption.setVisibility(View.GONE);

        if (photoItem.getCommentsCount() > 0) {
            viewHolder.tvViewAllComments.setText(String.format("view all %d comments", photoItem.getCommentsCount()));
            viewHolder.tvViewAllComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CommentsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PhotoItem photoItemSelected = getItem(position);
                    intent.putExtra("id", photoItemSelected.getId());
                    getContext().startActivity(intent);
                }
            });
            viewHolder.tvViewAllComments.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvViewAllComments.setVisibility(View.GONE);
        }

        // a couple of comments
        ArrayList<CommentItem> commentItems = photoItem.getCommentsArrayList();
        if (commentItems.size() > 0) {
            setComment(viewHolder.tvComment1, commentItems.get(commentItems.size() - 1));
        } else {
            viewHolder.tvComment1.setVisibility(View.GONE);
        }
        if (commentItems.size() > 1) {
            setComment(viewHolder.tvComment2, commentItems.get(commentItems.size() - 2));
        } else {
            viewHolder.tvComment2.setVisibility(View.GONE);
        }

        // use device width for photo height
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        viewHolder.imgPhoto.getLayoutParams().height = displayMetrics.widthPixels;
        // Reset the images from the recycled view
        viewHolder.imgProfile.setImageResource(0);
        viewHolder.imgPhoto.setImageResource(0);

        // Ask for the photo to be added to the imageview based on the photo url
        // Background: Send a network request to the url, download the image bytes, convert into bitmap, insert bitmap into the imageview
        Picasso.with(getContext()).load(photoItem.getProfileUrl()).into(viewHolder.imgProfile);

        // instead of ...
        //Picasso.with(getContext()).load(photoItem.getImageUrl()).
        //        placeholder(R.drawable.instagram_glyph_on_white).into(viewHolder.imgPhoto);
        viewHolder.imgPhoto.setCustomImageUrl(photoItem.getImageUrl());

        // Return the view for that data item
        return convertView;
    }

    private void setCaption(TextView tvCaption, PhotoItem photoItem) {

        tvCaption.setText(Html.fromHtml("<font color='#3f729b'><b>"
                + photoItem.getUserName() + "</b></font> "
                + photoItem.getCaption()));
        tvCaption.setVisibility(View.VISIBLE);
    }

    private void setComment(TextView tvComment, CommentItem commentItem) {

        tvComment.setText(Html.fromHtml("<font color='#3f729b'><b>"
                + commentItem.getUsername() + "</b></font> "
                + commentItem.getText()));
        tvComment.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isEnabled(int position) {
        // disables selection
        return false;
    }

    private static class ViewHolder {
        public ImageView imgProfile;
        public ExImageView imgPhoto;
        public TextView tvUsername;
        public TextView tvTime;
        public TextView tvLikes;
        public TextView tvCaption;
        public TextView tvViewAllComments;
        public TextView tvComment1;
        public TextView tvComment2;

    }
}
