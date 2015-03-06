package com.melnikacg.mytwitter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.io.Serializable;
import java.util.List;

public class FragmentTimeline extends ListFragment {

    public static String MY_ENUM = "TimeLineStatus";

    private TweetViewAdapter mTweetAdapter;
    private TimeLineStatus mTimeLineStatus;

    public static Fragment newInstance(int position) {
        FragmentTimeline f = new FragmentTimeline();
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                bundle.putSerializable(MY_ENUM, TimeLineStatus.HOME);
                break;
            case 1:
                bundle.putSerializable(MY_ENUM, TimeLineStatus.USER);
                break;
            case 2:
                bundle.putSerializable(MY_ENUM, TimeLineStatus.MENTION);
                break;
            default:
                bundle.putSerializable(MY_ENUM, TimeLineStatus.HOME);
        }
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimeLineStatus = (TimeLineStatus) getArguments().getSerializable(MY_ENUM);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mTweetAdapter = new TweetViewAdapter(getActivity());
        setListAdapter(mTweetAdapter);

        loadTweets();

        View v = inflater.inflate(R.layout.activity_list, container, false);
        return v;
    }

    public void loadTweets() {
        final StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();

        switch (mTimeLineStatus) {
            case HOME:
                service.homeTimeline(null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                mTweetAdapter.setTweets(result.data);
                            }

                            @Override
                            public void failure(TwitterException error) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failed_timeline) + error.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;
            case USER:
                service.userTimeline(null, null, null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                mTweetAdapter.setTweets(result.data);
                            }

                            @Override
                            public void failure(TwitterException error) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failed_timeline) + error.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;
            case MENTION:
                service.mentionsTimeline(null, null, null, null, null, null, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                mTweetAdapter.setTweets(result.data);
                            }

                            @Override
                            public void failure(TwitterException error) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failed_timeline) + error.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;
        }
    }

    public static enum TimeLineStatus implements Serializable {
        HOME, USER, MENTION;
    }
}