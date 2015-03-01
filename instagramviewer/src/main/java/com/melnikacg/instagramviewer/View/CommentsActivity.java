package com.melnikacg.instagramviewer.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnikacg.instagramviewer.Model.CommentItem;
import com.melnikacg.instagramviewer.Model.Comments;
import com.melnikacg.instagramviewer.Model.Constants;
import com.melnikacg.instagramviewer.Presenter.CommentsItemAdapter;
import com.melnikacg.instagramviewer.R;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import java.util.ArrayList;

public class CommentsActivity extends BaseSampleSpiceActivity {
    //public static final String CLIENT_ID = "c8e2cde3f35d402687512d9004ee7b12";

    private ArrayList<CommentItem> mListComments;
    private CommentsItemAdapter mAdapterComments;

    private SimpleTextRequest mCommentsRequest;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        fetchComments();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSpiceManager().execute(mCommentsRequest, "txt", DurationInMillis.ONE_SECOND, new LoremRequestListener());
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


    public final class LoremRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(CommentsActivity.this, "failure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final String result) {

            Toast.makeText(CommentsActivity.this, "success", Toast.LENGTH_SHORT).show();

            final Gson gson = new Gson();

            mListComments.clear();
            mListComments.addAll(gson.fromJson(result, Comments.class).getCommentItems());
            mAdapterComments.notifyDataSetChanged();

        }
    }

    private void fetchComments() {

        mListComments = new ArrayList<CommentItem>(); // initialize arraylist
        // Create adapter bind it to the data in arraylist
        mAdapterComments = new CommentsItemAdapter(this, mListComments);
        // Populate the data into the listview
        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        // Set the adapter to the listview (population of items)
        lvComments.setAdapter(mAdapterComments);

        String commentsUrl = Constants.MEDIA_URL + id + Constants.COMMENTS_CLIENT_URL
                + Constants.CLIENT_ID;

        mCommentsRequest = new SimpleTextRequest(commentsUrl);

    }
}
