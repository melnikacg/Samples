package com.melnikacg.instagramviewer.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnikacg.instagramviewer.Model.CommentItem;
import com.melnikacg.instagramviewer.Presenter.CommentsItemAdapter;
import com.melnikacg.instagramviewer.R;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsActivity extends BaseSampleSpiceActivity {
    public static final String CLIENT_ID = "c8e2cde3f35d402687512d9004ee7b12";

    //old
    //private ArrayList<Comment> comments;
    //new
    private ArrayList<CommentItem> comments;

    //old
    //private CommentsAdapter aComments;
    //new
    private CommentsItemAdapter aComments;

    //new
    private SimpleTextRequest loremRequest;

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

        getSpiceManager().execute(loremRequest, "txt", DurationInMillis.ONE_SECOND, new LoremRequestListener());
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

            JSONArray commentsJSON = null;
            final Gson gson = new Gson();

            try {
                comments.clear();

                JSONObject array = new JSONObject(result);
                //jObject = jsonArray.getJSONObject(0);
                commentsJSON = array.getJSONArray("data");

                // put newest at the top
                for (int i = commentsJSON.length() - 1; i >= 0; i--) {
                    comments.add(gson.fromJson(commentsJSON.getJSONObject(i).toString(), CommentItem.class));
                }
                // Notified the adapter that it should populate new changes into the listview
                aComments.notifyDataSetChanged();
            } catch (JSONException e ) {
                // Fire if things fail, json parsing is invalid
                e.printStackTrace();
            }
        }
    }


    private void fetchComments() {

        //old
        //comments = new ArrayList<Comment>(); // initialize arraylist

        //new
        comments = new ArrayList<CommentItem>(); // initialize arraylist

        // Create adapter bind it to the data in arraylist
        aComments = new CommentsItemAdapter(this, comments);
        // Populate the data into the listview
        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        // Set the adapter to the listview (population of items)
        lvComments.setAdapter(aComments);
        // https://api.instagram.com/v1/media/<id>/comments?client_id=<clientid>
        // Setup comments url endpoint
        String commentsUrl = "https://api.instagram.com/v1/media/" + id + "/comments?client_id=" + CLIENT_ID;



        loremRequest = new SimpleTextRequest(commentsUrl);
        //getSpiceManager().execute(loremRequest, "txt", DurationInMillis.ONE_MINUTE, new LoremRequestListener());


        /*

        // Create the network client
        AsyncHttpClient client = new AsyncHttpClient();

        // Trigger the network request
        client.get(commentsUrl, new JsonHttpResponseHandler() {
            // define success and failure callbacks
            // Handle the successful response (popular photos JSON)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // fired once the successful response back
                // resonse is == comments json

                Gson gson = new Gson();

                JSONArray commentsJSON = null;
                try {
                    comments.clear();

                    //old
                    commentsJSON = response.getJSONArray("data");
                    //old

                    //new
                    //JSONObject array = new JSONObject(result);
                    //


                    // put newest at the top
                    for (int i = commentsJSON.length() - 1; i >= 0; i--) {
                        JSONObject commentJSON = commentsJSON.getJSONObject(i);

                        comments.add(gson.fromJson(commentJSON.toString(), CommentItem.class));

                        //Comment comment = new Comment();
                        //comment.profileUrl = commentJSON.getJSONObject("from").getString("profile_picture");
                        //comment.username = commentJSON.getJSONObject("from").getString("username");
                        //comment.text = commentJSON.getString("text");
                        //comment.createdTime = commentJSON.getString("created_time");
                        //comments.add(comment);
                    }
                    // Notified the adapter that it should populate new changes into the listview
                    aComments.notifyDataSetChanged();
                } catch (JSONException e ) {
                    // Fire if things fail, json parsing is invalid
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        */

    }
}
