package com.melnikacg.instagrammaterial.Presenter;

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

import com.melnikacg.instagrammaterial.Model.CommentItem;
import com.melnikacg.instagrammaterial.Model.PhotoItem;
import com.melnikacg.instagrammaterial.R;
import com.melnikacg.instagrammaterial.View.CommentsActivity;
import com.melnikacg.instagrammaterial.View.ExImageView;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_material, parent, false);

            viewHolder = new ViewHolder();
            // Lookup the subview within the template
            viewHolder.imageViewProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
            viewHolder.imageViewPhoto = (ExImageView) convertView.findViewById(R.id.imgPhoto);
            viewHolder.textViewUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.textViewTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.textViewLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.textViewCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.textViewViewAllComments = (TextView) convertView.findViewById(R.id.tvViewAllComments);
            viewHolder.textView1stComment = (TextView) convertView.findViewById(R.id.tvComment1);
            viewHolder.textView2ndComment = (TextView) convertView.findViewById(R.id.tvComment2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhotoItem photoItem = getItem(position);
        viewHolder.textViewUsername.setText(photoItem.getUserName());
        viewHolder.textViewTime.setText(photoItem.getRelativeTime());
        viewHolder.textViewLikes.setText(String.format("%d likes", photoItem.getLikesCount()));

        if (photoItem.getCaption() != null)
            setCaption(viewHolder.textViewCaption, photoItem);
        else
            viewHolder.textViewCaption.setVisibility(View.GONE);

        if (photoItem.getCommentsCount() > 0) {
            viewHolder.textViewViewAllComments.setText(String.format("view all %d comments", photoItem.getCommentsCount()));
            viewHolder.textViewViewAllComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CommentsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PhotoItem photoItemSelected = getItem(position);
                    intent.putExtra("id", photoItemSelected.getId());
                    getContext().startActivity(intent);
                }
            });
            viewHolder.textViewViewAllComments.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textViewViewAllComments.setVisibility(View.GONE);
        }

        // a couple of comments
        ArrayList<CommentItem> commentItems = photoItem.getCommentsArrayList();
        if (commentItems.size() > 0) {
            setComment(viewHolder.textView1stComment, commentItems.get(commentItems.size() - 1));
        } else {
            viewHolder.textView1stComment.setVisibility(View.GONE);
        }
        if (commentItems.size() > 1) {
            setComment(viewHolder.textView2ndComment, commentItems.get(commentItems.size() - 2));
        } else {
            viewHolder.textView2ndComment.setVisibility(View.GONE);
        }

        // use device width for photo height
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        viewHolder.imageViewPhoto.getLayoutParams().height = displayMetrics.widthPixels;
        // Reset the images from the recycled view
        viewHolder.imageViewProfile.setImageResource(0);
        viewHolder.imageViewPhoto.setImageResource(0);

        // Ask for the photo to be added to the imageview based on the photo url
        // Background: Send a network request to the url, download the image bytes, convert into bitmap, insert bitmap into the imageview
        Picasso.with(getContext()).load(photoItem.getProfileUrl()).into(viewHolder.imageViewProfile);

        // instead of ...
        //Picasso.with(getContext()).load(photoItem.getImageUrl()).
        //        placeholder(R.drawable.instagram_glyph_on_white).into(viewHolder.imageViewPhoto);
        viewHolder.imageViewPhoto.setCustomImageUrl(photoItem.getImageUrl());

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
        public ImageView imageViewProfile;
        public ExImageView imageViewPhoto;
        public TextView textViewUsername;
        public TextView textViewTime;
        public TextView textViewLikes;
        public TextView textViewCaption;
        public TextView textViewViewAllComments;
        public TextView textView1stComment;
        public TextView textView2ndComment;

    }
}
