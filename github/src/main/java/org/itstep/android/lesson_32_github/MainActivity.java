package org.itstep.android.lesson_32_github;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int RESULT_CODE_GITHUB = 100;

    private String mAccessToken;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);

        mImageLoader = new ImageLoader(mRequestQueue,
            new ImageLoader.ImageCache() {

                private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            });

        findViewById(R.id.auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                authWithGithub();
            }
        });

        findViewById(R.id.repos_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                getRepos();
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_CODE_GITHUB:
                if (resultCode == RESULT_OK) {
                    mAccessToken = data
                            .getStringExtra(GithubAuthActivity.EXTRA_KEY_ACCESS_TOKEN);

                    performUserRequest();
                } else {
                    Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void performUserRequest() {
        mRequestQueue.add(new GitHubUserRequest(mAccessToken, listener, errorListener));
    }

    private void authWithGithub() {
        final Intent intent = new Intent(this, GithubAuthActivity.class);
        startActivityForResult(intent, RESULT_CODE_GITHUB);
    }

    final Response.Listener<GitHubUser> listener = new Response.Listener<GitHubUser>() {
        @Override
        public void onResponse(final GitHubUser response) {
            Log.i(TAG, "Success");

            final TextView login = (TextView) findViewById(R.id.login);
            login.setText(response.getLogin());

            NetworkImageView avatar = (NetworkImageView) findViewById(R.id.avatar);
            avatar.setImageUrl(response.getAvatarUrl(), mImageLoader);
        }
    };

    final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(final VolleyError error) {
            Log.e(TAG, "Error", error);
        }
    };


    private void getRepos() {
        mRequestQueue.add(new GetReposRequest(mAccessToken, repoListener, errorListener));
    }

    final Response.Listener<GitHubRepos> repoListener = new Response.Listener<GitHubRepos>() {
        @Override
        public void onResponse(final GitHubRepos response) {
            Log.i(TAG, "Success");

            final TextView repos = (TextView) findViewById(R.id.repos);
            for (GitHubRepo repo : response) {
                repos.append(repo.getName() + "\n");
                repos.append(repo.getCreatedAt() + "\n");
                repos.append(repo.getLanguage() + "\n");
            }
        }
    };
}