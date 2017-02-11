package eu.laramartin.popularmovies.api;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import eu.laramartin.popularmovies.BuildConfig;
import eu.laramartin.popularmovies.data.Trailer;
import eu.laramartin.popularmovies.data.TrailerCollection;

/**
 * Created by lara on 11.02.17.
 */

public abstract class FetchTrailersTask extends AsyncTask<String, Void, List<Trailer>> {

    private static final String LOG_TAG = FetchTrailersTask.class.getSimpleName();
    private String id;

    public FetchTrailersTask(String id) {
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
}
