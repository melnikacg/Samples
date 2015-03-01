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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InstagramPhotosItemAdapter extends ArrayAdapter<PhotoItem> {

    public InstagramPhotosItemAdapter(Context context, List<PhotoItem> photos) {
        super(context, android.R.layout.simple_list_item_1, photos);
    }

    // getView method (int position)
    // Default, takes the model (InstagramPhoto) toString()

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Take the data source at position (e.g. 0)
        // Get the data item
        PhotoItem photoItem = getItem(position);

        // Check if we are using a recycled view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // Lookup the subview within the template
        ImageView imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvViewAllComments = (TextView) convertView.findViewById(R.id.tvViewAllComments);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);

        tvUsername.setText(photoItem.getUserName());
        tvTime.setText(photoItem.getRelativeTime());

        tvLikes.setText(String.format("%d likes", photoItem.getLikesCount()));

        if (photoItem.getCaption() != null) {
            setCaption(tvCaption, photoItem);

        } else {
            tvCaption.setVisibility(View.GONE);
        }

        if (photoItem.getCommentsCount() > 0) {
            tvViewAllComments.setText(String.format("view all %d comments", photoItem.getCommentsCount()));
            tvViewAllComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CommentsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PhotoItem photoItemSelected = getItem(position);
                    intent.putExtra("id", photoItemSelected.getId());
                    getContext().startActivity(intent);
                }
            });
            tvViewAllComments.setVisibility(View.VISIBLE);
        } else {
            tvViewAllComments.setVisibility(View.GONE);
        }

        ArrayList<CommentItem> commentItems = photoItem.getCommentsArrayList();

        if (commentItems.size() > 0) {
            setComment(tvComment1, commentItems.get(commentItems.size() - 1));

        } else {
            tvComment1.setVisibility(View.GONE);
        }

        if (commentItems.size() > 1) {
            setComment(tvComment2, commentItems.get(commentItems.size() - 2));

        } else {
            tvComment2.setVisibility(View.GONE);
        }

        // use device width for photo height
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        imgPhoto.getLayoutParams().height = displayMetrics.widthPixels;

        // Reset the images from the recycled view
        imgProfile.setImageResource(0);
        imgPhoto.setImageResource(0);

        // Ask for the photo to be added to the imageview based on the photo url
        // Background: Send a network request to the url, download the image bytes, convert into bitmap, insert bitmap into the imageview
        Picasso.with(getContext()).load(photoItem.getProfileUrl()).into(imgProfile);
        Picasso.with(getContext()).load(photoItem.getImageUrl()).placeholder(R.drawable.instagram_glyph_on_white).into(imgPhoto);
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
}
