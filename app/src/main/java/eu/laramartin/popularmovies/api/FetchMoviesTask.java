package eu.laramartin.popularmovies.api;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import eu.laramartin.popularmovies.BuildConfig;
import eu.laramartin.popularmovies.ui.MoviesAdapter;
import eu.laramartin.popularmovies.data.Movie;
import eu.laramartin.popularmovies.data.MoviesResponse;

/**
 * Created by lara on 11.02.17.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    private MoviesAdapter adapter;

    public FetchMoviesTask(MoviesAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        URL moviesRequestUrl = NetworkUtils.buildUrl(BuildConfig.API_KEY, params[0]);
        try {
            String jsonMoviesResponse = NetworkUtils
                    .getResponseFromHttpUrl(moviesRequestUrl);
            MoviesResponse moviesResponse = MoviesJsonUtils.parseJson(jsonMoviesResponse);
            return moviesResponse.getMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
//            Log.v(MainActivity.LOG_TAG, "moviesData: " + movies);
            adapter.setMoviesData(movies);
        }
    }
}
