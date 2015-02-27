package com.melnikacg.instagramviewer.View;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnikacg.instagramviewer.Presenter.InstagramPhotosItemAdapter;
import com.melnikacg.instagramviewer.Model.PhotoItem;
import com.melnikacg.instagramviewer.R;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends BaseSampleSpiceActivity {

    public static final String CLIENT_ID = "c8e2cde3f35d402687512d9004ee7b12";

    private ArrayList<PhotoItem> mPhotos;

    private InstagramPhotosItemAdapter aPhotos;

    private SwipeRefreshLayout mSwipeContainer;

    //new
    private SimpleTextRequest mPhotoRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
                //getSpiceManager().execute(mPhotoRequest, "txt", DurationInMillis.ONE_MINUTE, new PhotoRequestListener());
            }
        });

        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        mPhotos = new ArrayList<PhotoItem>(); // initialize arraylist
        // Create adapter bind it to the data in arraylist
        aPhotos = new InstagramPhotosItemAdapter(this, mPhotos);
        // Populate the data into the listview
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // Set the adapter to the listview (population of items)
        lvPhotos.setAdapter(aPhotos);
        // https://api.instagram.com/v1/media/popular?client_id=<clientid>
        // { "data" => [x] => "images" => "standard_resolution" => "url" }
        // Setup popular url endpoint
        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        // Create the network client
        //AsyncHttpClient client = new AsyncHttpClient();

        mPhotoRequest = new SimpleTextRequest(popularUrl);
        //getSpiceManager().execute(mPhotoRequest, "txt", DurationInMillis.ONE_MINUTE, new LoremRequestListener());

        getSpiceManager().execute(mPhotoRequest, "txt", DurationInMillis.ONE_SECOND, new PhotoRequestListener());
    }

    public final class PhotoRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(PhotosActivity.this, "failure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final String result) {

            final Gson gson = new Gson();

            JSONArray photosJSON = null;
            JSONArray commentsJSON = null;

            try {

                mPhotos.clear();

                JSONObject array = new JSONObject(result);
                photosJSON = array.getJSONArray("data");
                //commentsJSON = array.getJSONArray("data");

                for (int i = 0; i < photosJSON.length(); i++) {


                    //JSONObject photoJSON = photosJSON.getJSONObject(i); // 1, 2, 3, 4

                    mPhotos.add(gson.fromJson(photosJSON.getJSONObject(i).toString(), PhotoItem.class));

                    /*
                    // Get last 2 comments
                    if (photoJSON.has("comments") && !photoJSON.isNull("comments")) {
                        photo.commentsCount = photoJSON.getJSONObject("comments").getInt("count");

                        commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");

                        if (commentsJSON.length() > 0) {
                            photo.comment1 = commentsJSON.getJSONObject(commentsJSON.length() - 1).getString("text");
                            photo.user1 = commentsJSON.getJSONObject(commentsJSON.length() - 1).getJSONObject("from").getString("username");
                            if (commentsJSON.length() > 1) {
                                photo.comment2 = commentsJSON.getJSONObject(commentsJSON.length() - 2).getString("text");
                                photo.user2 = commentsJSON.getJSONObject(commentsJSON.length() - 2).getJSONObject("from").getString("username");
                            }
                        } else {
                            photo.commentsCount = 0;
                        }
                    }
                    */

                }
                // Notified the adapter that it should populate new changes into the listview
                aPhotos.notifyDataSetChanged();
            } catch (JSONException e) {
                // Fire if things fail, json parsing is invalid
                e.printStackTrace();
            }
            mSwipeContainer.setRefreshing(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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

    @Override
    protected void onStart() {
        super.onStart();

        fetchPopularPhotos();
    }

}
