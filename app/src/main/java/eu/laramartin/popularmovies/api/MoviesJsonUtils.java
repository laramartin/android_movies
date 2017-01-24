package eu.laramartin.popularmovies.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.laramartin.popularmovies.db.Movie;
import eu.laramartin.popularmovies.db.Response;

/**
 * Created by lara on 24/1/17.
 */
public class MoviesJsonUtils {

    private static final String LOG_TAG = MoviesJsonUtils.class.getCanonicalName();

    public static Response parseJson(String json)
            throws JSONException {

        final String statusError = "status_code";
        final String movies = "results";
        final String posterKey = "poster_path";
        final String overviewKey = "overview";
        final String releaseDateKey = "release_date";
        final String titleKey = "title";
        final String idKey = "id";
        final String voteAverageKey = "vote_average";

        JSONObject responseJson = new JSONObject(json);

        if (responseJson.has(statusError)) {
            int errorCode = responseJson.getInt(statusError);
            Log.e(LOG_TAG, "parse json error code: " + String.valueOf(errorCode));
        }

        JSONArray moviesArray = responseJson.getJSONArray(movies);
        List<Movie> movieList = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            Movie currentMovie = new Movie();
            currentMovie.setPosterPath(movie.getString(posterKey));
            currentMovie.setOverview(movie.getString(overviewKey));
            currentMovie.setReleaseDate(movie.getString(releaseDateKey));
            currentMovie.setTitle(movie.getString(titleKey));
            currentMovie.setId(movie.getInt(idKey));
            currentMovie.setVoteAverage(movie.getLong(voteAverageKey));
            movieList.add(currentMovie);
        }
        Response response = new Response();
        response.setMovies(movieList);
        return response;
    }
}
