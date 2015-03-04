package com.melnikacg.mytwitter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;

import java.util.List;

public class FragmentUserTimeline extends ListFragment {

    private TweetViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new TweetViewAdapter(getActivity());
        setListAdapter(adapter);
        loadTweets();

        return inflater.inflate(R.layout.activity_list, container, false);
    }

    public void loadTweets() {
        final StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();

        service.userTimeline(null, null, null, null, null, null, null, null, null, new Callback<List<Tweet>>() {

                    @Override
                    public void success(Result<List<Tweet>> result) {
                        adapter.setTweets(result.data);
                    }

                    @Override
                    public void failure(TwitterException error) {
                        Toast.makeText(getActivity(), "Failed to retrieve timeline " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}