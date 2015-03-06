package com.melnikacg.instagramviewer.Presenter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnikacg.instagramviewer.Model.CommentItem;
import com.melnikacg.instagramviewer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsItemAdapter extends ArrayAdapter<CommentItem> {

    public CommentsItemAdapter(Context context, List<CommentItem> comments) {
        super(context, android.R.layout.simple_list_item_1, comments);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgProfile = (ImageView) convertView.findViewById(R.id.imgCommentProfile);
            viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
            viewHolder.tvCommentTime = (TextView) convertView.findViewById(R.id.tvCommentTime);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommentItem comment = getItem(position);

        viewHolder.tvComment.setText(Html.fromHtml("<font color='#3f729b'><b>"
                + comment.getUsername() + "</b></font> "
                + comment.getText()));
        viewHolder.tvCommentTime.setText(comment.getRelativeTime());

        viewHolder.imgProfile.setImageResource(0);
        Picasso.with(getContext()).load(comment.getProfileUrl()).into(viewHolder.imgProfile);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        // disables selection
        return false;
    }

    private static class ViewHolder {

        public ImageView imgProfile;
        public TextView tvComment;
        public TextView tvCommentTime;

    }
}
