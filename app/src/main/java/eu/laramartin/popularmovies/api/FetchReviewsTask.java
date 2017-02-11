package eu.laramartin.popularmovies.api;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.List;

import eu.laramartin.popularmovies.BuildConfig;
import eu.laramartin.popularmovies.data.Review;
import eu.laramartin.popularmovies.data.ReviewCollection;

/**
 * Created by lara on 11.02.17.
 */

public class FetchReviewsTask extends AsyncTask<String, Void, List<Review>> {

    private static final String LOG_TAG = FetchReviewsTask.class.getSimpleName();
    private String id;

    public FetchReviewsTask(String id) {
        this.id = id;
    }

    @Override
    protected List<Review> doInBackground(String... params) {
        URL reviewsRequestUrl = NetworkUtils.buildTrailersOrReviewsUrl(BuildConfig.API_KEY, id, "reviews");
        try {
            String jsonReviewsResponse = NetworkUtils
                    .getResponseFromHttpUrl(reviewsRequestUrl);
            ReviewCollection reviewCollection = ReviewsJsonUtils.parseJson(jsonReviewsResponse);
            return reviewCollection.getReviews();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        if (reviews != null) {
            for (int i = 0; i < reviews.size(); i++) {
                Log.v(LOG_TAG, "review author: " + reviews.get(i).getAuthor() +
                        " \ncontent: " + reviews.get(i).getContent());
            }
        }
    }
}
