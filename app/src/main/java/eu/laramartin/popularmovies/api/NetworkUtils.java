package eu.laramartin.popularmovies.api;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lara on 24/1/17.
 */

public class NetworkUtils {

    // http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
    private static final String LOG_TAG = NetworkUtils.class.getCanonicalName();


    public static URL buildUrl(String apiKey) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter("api_key", "1");

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);
        return url;
    }
}
