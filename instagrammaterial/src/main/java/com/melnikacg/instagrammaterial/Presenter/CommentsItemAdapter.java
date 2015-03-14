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
    private final List<CommentItem> mListComments = new ArrayList<>();

    // Animation
    // Allows to remember the last item shown on screen
    private int mLastPosition = -1;

    public CommentsItemAdapter(final Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.item_comment_material, viewGroup, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder commentsViewHolder, int position) {
        commentsViewHolder.update(mListComments.get(position));
        //Animation
        setAnimation(commentsViewHolder.cardViewContainer, position);
    }

    @Override
    public int getItemCount() {
        return mListComments.size();
    }

    public void addAll(final Collection<CommentItem> items) {
        mListComments.addAll(items);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mListComments.clear();
        notifyDataSetChanged();
    }

    //Animation
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > mLastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewProfile;
        private final TextView textViewComment;
        private final TextView textViewCommentTime;
        //Animation
        private final CardView cardViewContainer;

        public CommentsViewHolder(final View itemView) {
            super(itemView);

            imageViewProfile = (ImageView) itemView.findViewById(R.id.imgCommentProfile);
            textViewComment = (TextView) itemView.findViewById(R.id.tvComment);
            textViewCommentTime = (TextView) itemView.findViewById(R.id.tvCommentTime);

            //Animation
            cardViewContainer = (CardView) itemView.findViewById(R.id.cardContainer);
        }

        public void update(final CommentItem comment) {

            textViewComment.setText(Html.fromHtml("<font color='#3f729b'><b>"
                    + comment.getUsername() + "</b></font> "
                    + comment.getText()));
            textViewCommentTime.setText(comment.getRelativeTime());

            imageViewProfile.setImageResource(0);
            Picasso.with(imageViewProfile.getContext()).load(comment.getProfileUrl()).into(imageViewProfile);

        }
    }

}
