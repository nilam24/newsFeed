package in.cdac.newsfeedapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell1 on 28/01/2018.
 */

public class NewsFeedLoader extends AsyncTaskLoader<List<NewsPojo>> {

    List<NewsPojo> newsPojoList = new ArrayList<NewsPojo>();
    Context context;
    String requestUrl;


    public NewsFeedLoader(Context context) {
        super(context);
        this.context = context;

    }


    public NewsFeedLoader(Context context, String requestUrl) {
        super(context);
        this.context = context;
        this.requestUrl = requestUrl;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    public List<NewsPojo> loadInBackground() {

        List<NewsPojo> pojoList = new ArrayList<NewsPojo>();

        if (requestUrl == null) {
            return null;
        }

        pojoList = QueryUtilsNewsFeed.fetchDataFromQueryUtils(requestUrl);


        return pojoList;
    }
}
