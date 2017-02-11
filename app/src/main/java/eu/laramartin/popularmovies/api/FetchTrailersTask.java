package eu.laramartin.popularmovies.api;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.List;

import eu.laramartin.popularmovies.BuildConfig;
import eu.laramartin.popularmovies.data.Trailer;
import eu.laramartin.popularmovies.data.TrailerCollection;

/**
 * Created by lara on 11.02.17.
 */

public class FetchTrailersTask extends AsyncTask<String, Void, List<Trailer>> {

    private static final String LOG_TAG = FetchTrailersTask.class.getSimpleName();
    private String id;

    public FetchTrailersTask(String id) {
//        this.adapter = adapter;
        this.id = id;
    }

    @Override
    protected List<Trailer> doInBackground(String... params) {
        URL trailersRequestUrl = NetworkUtils.buildTrailersOrReviewsUrl(BuildConfig.API_KEY, id, "videos");
        try {
            String jsonTrailersResponse = NetworkUtils
                    .getResponseFromHttpUrl(trailersRequestUrl);
            TrailerCollection trailerCollection = TrailersJsonUtils.parseJson(jsonTrailersResponse);
            return trailerCollection.getTrailers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        if (trailers != null) {
            for (int i = 0; i < trailers.size(); i++) {
                Log.v(LOG_TAG, "trailer name: " + trailers.get(i).getName() +
                " \ntype: " + trailers.get(i).getType() +
                " \nsite: " + trailers.get(i).getSite());
            }
        }
    }
}
