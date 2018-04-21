package in.cdac.newsfeedapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsPojo>> {

    TextView emptyView;
    ListView newsListView;
    NewsPojo newsPojo;
    Uri uri = null;
    final Bitmap bitmap = null;

    private String REQUEST_URL = "https://content.guardianapis.com/search?&show-tags=contributor&show-element=image&order-by=newest&api-key=test";
    private static String TAG = MainActivity.class.getName();
    private static int NEWS_FEED_LOADER_ID = 1;

    List<NewsPojo> pojoList;
    NewsFeedAdapter feedAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emptyView = (TextView) findViewById(R.id.textFindEmptyView);
        newsListView = (ListView) findViewById(R.id.listViewNewsFeed);
        newsListView.setEmptyView(emptyView);

        newsPojo = new NewsPojo();

        feedAdapter = new NewsFeedAdapter(this, new ArrayList<NewsPojo>(), bitmap);
        newsListView.setAdapter(feedAdapter);
        feedAdapter.notifyDataSetChanged();


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                newsPojo = (NewsPojo) adapterView.getItemAtPosition(position);

                Log.e(TAG, "newpojo=" + newsPojo);

                if (newsPojo != null) {

                    uri = Uri.parse(newsPojo.getWebUrl());

                } else {
                    Log.e("newsPojo", "is null");
                }

                Toast.makeText(MainActivity.this, "Opening in Browser=" + uri, Toast.LENGTH_LONG).show();

                Log.e(TAG, "browser is loading....." + uri);
                Intent in = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(in);

            }
        });


        try {
            ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {

                getLoaderManager().initLoader(NEWS_FEED_LOADER_ID, null, this);

            } else {
                emptyView.setText("no network");
            }
        } catch (NullPointerException n) {
            Log.e(TAG, "exception:" + n.getMessage());
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        newsPojo = new NewsPojo();

    }


    @Override
    public Loader<List<NewsPojo>> onCreateLoader(int i, Bundle bundle) {

        NewsFeedLoader newsFeedLoader = new NewsFeedLoader(this, REQUEST_URL);

        return newsFeedLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<NewsPojo>> loader, List<NewsPojo> newsPojoList) {

        if (newsPojoList == null) {

            emptyView.setText(R.string.textView_EmptyView);
        }

        feedAdapter.clear();

        if ((newsPojoList != null) && (!newsPojoList.isEmpty())) {
            feedAdapter.addAll(newsPojoList);

        }

    }

    @Override
    public void onLoaderReset(Loader<List<NewsPojo>> loader) {

        feedAdapter.clear();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("", REQUEST_URL);
        feedAdapter.getPosition(newsPojo);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        if (uri != null) {
            return uri;
        }

        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

