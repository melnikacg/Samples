package com.melnikacg.instagrammaterial.Presenter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnikacg.instagrammaterial.Model.CommentItem;
import com.melnikacg.instagrammaterial.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommentsItemAdapter extends RecyclerView.Adapter<CommentsItemAdapter.CommentsViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final List<CommentItem> mList = new ArrayList<>();

    //anim
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    //new
    public CommentsItemAdapter(final Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    //new
    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.item_comment_material, viewGroup, false);
        return new CommentsViewHolder(view);
    }

    //new
    @Override
    public void onBindViewHolder(CommentsViewHolder commentsViewHolder, int position) {
        commentsViewHolder.update(mList.get(position));

        setAnimation(commentsViewHolder.cardContainer, position);
    }

    //new
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(final Collection<CommentItem> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mList.clear();
        notifyDataSetChanged();
    }


    //anim
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    //new
    public static class CommentsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final TextView tvComment;
        private final TextView tvCommentTime;

        //anim
        private final CardView cardContainer;

        public CommentsViewHolder(final View itemView) {
            super(itemView);

            imgProfile = (ImageView) itemView.findViewById(R.id.imgCommentProfile);
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);
            tvCommentTime = (TextView) itemView.findViewById(R.id.tvCommentTime);

            //anim
            cardContainer = (CardView) itemView.findViewById(R.id.cardContainer);

        }

        public void update(final CommentItem comment) {

            tvComment.setText(Html.fromHtml("<font color='#3f729b'><b>"
                    + comment.getUsername() + "</b></font> "
                    + comment.getText()));
            tvCommentTime.setText(comment.getRelativeTime());

            imgProfile.setImageResource(0);
            Picasso.with(imgProfile.getContext()).load(comment.getProfileUrl()).into(imgProfile);

        }
    }


}
