package com.melnikacg.instagrammaterial.View;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnikacg.instagrammaterial.Model.Constants;
import com.melnikacg.instagrammaterial.Model.PhotoItem;
import com.melnikacg.instagrammaterial.Model.Photos;
import com.melnikacg.instagrammaterial.Presenter.PhotosItemAdapter;
import com.melnikacg.instagrammaterial.R;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import java.util.ArrayList;

public class PhotosActivity extends BaseSampleSpiceActivity {

    private ArrayList<PhotoItem> mListPhotos;
    private PhotosItemAdapter mAdapterPhotos;
    private SwipeRefreshLayout mSwipeContainer;
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
            }
        });

        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void fetchPopularPhotos() {

        mListPhotos = new ArrayList<PhotoItem>();
        mAdapterPhotos = new PhotosItemAdapter(this, mListPhotos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(mAdapterPhotos);

        // Setup popular url endpoint
        String popularUrl = Constants.POPULAR_URL + Constants.CLIENT_ID;
        mPhotoRequest = new SimpleTextRequest(popularUrl);
        getSpiceManager().execute(mPhotoRequest, "txt", DurationInMillis.ONE_SECOND, new RequestPhotoListener());
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

    public final class RequestPhotoListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(PhotosActivity.this, "failure", Toast.LENGTH_SHORT).show();
            mSwipeContainer.setRefreshing(false);
        }

        @Override
        public void onRequestSuccess(final String result) {

            final Gson gson = new Gson();

            mListPhotos.clear();
            mListPhotos.addAll(gson.fromJson(result, Photos.class).getPhotoItems());
            mAdapterPhotos.notifyDataSetChanged();
            mSwipeContainer.setRefreshing(false);
        }
    }

}
