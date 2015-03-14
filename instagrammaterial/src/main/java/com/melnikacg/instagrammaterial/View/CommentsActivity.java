package com.melnikacg.instagrammaterial.View;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnikacg.instagrammaterial.Model.CommentItem;
import com.melnikacg.instagrammaterial.Model.Comments;
import com.melnikacg.instagrammaterial.Model.Constants;
import com.melnikacg.instagrammaterial.Presenter.CommentsItemAdapter;
import com.melnikacg.instagrammaterial.R;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import java.util.ArrayList;
import java.util.Collections;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class CommentsActivity extends BaseSampleSpiceActivity {
    //public static final String CLIENT_ID = "c8e2cde3f35d402687512d9004ee7b12";

    private ArrayList<CommentItem> mListComments;

    private RecyclerView mRecyclerView;
    private CommentsItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SimpleTextRequest mCommentsRequest;
    private String mIdComment;
    private SwipeRefreshLayout mSwipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mIdComment = getIntent().getStringExtra("id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);

        mAdapter = new CommentsItemAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_comments);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        final SlideInLeftAnimator animator = new SlideInLeftAnimator();

        fetchComments();
        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerComments);

        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchComments();
                getSpiceManager().execute(mCommentsRequest, "txt", DurationInMillis.ONE_SECOND, new RequestCommentsListener());
            }
        });

        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSpiceManager().execute(mCommentsRequest, "txt", DurationInMillis.ONE_SECOND, new RequestCommentsListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchComments() {

        mListComments = new ArrayList<CommentItem>();

        String commentsUrl = Constants.MEDIA_URL + mIdComment + Constants.COMMENTS_CLIENT_URL
                + Constants.CLIENT_ID;
        mCommentsRequest = new SimpleTextRequest(commentsUrl);
    }

    public final class RequestCommentsListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(CommentsActivity.this, "failure", Toast.LENGTH_SHORT).show();
            mSwipeContainer.setRefreshing(false);
        }

        @Override
        public void onRequestSuccess(final String result) {

            mListComments.clear();
            mAdapter.clearAll();

            final Gson gson = new Gson();
            ArrayList<CommentItem> listComments = gson.fromJson(result, Comments.class).getCommentItems();
            Collections.reverse(listComments);

            mListComments.addAll(listComments);
            mAdapter.addAll(mListComments);

            mSwipeContainer.setRefreshing(false);
        }
    }
}
