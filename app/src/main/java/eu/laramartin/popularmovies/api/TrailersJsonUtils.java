package eu.laramartin.popularmovies.api;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.laramartin.popularmovies.data.Trailer;
import eu.laramartin.popularmovies.data.TrailerCollection;

/**
 * Created by lara on 11.02.17.
 */

public class TrailersJsonUtils {

    private static final String LOG_TAG = TrailersJsonUtils.class.getCanonicalName();
    private static final String statusError = "status_code";
    private static final String trailers = "results";
    private static final String key = "key";
    private static final String name = "name";
    private static final String site = "site";
    private static final String type = "type";

    public static TrailerCollection parseJson(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(statusError)) {
            int errorCode = responseJson.getInt(statusError);
            Log.e(LOG_TAG, "parse json trailers error code: " + String.valueOf(errorCode));
        }
        JSONArray trailersArray = responseJson.getJSONArray(trailers);
        List<Trailer> trailerList = parseTrailerList(trailersArray);
        TrailerCollection trailerCollection = new TrailerCollection();
        trailerCollection.setTrailers(trailerList);
        return trailerCollection;
    }

    @NonNull
    private static List<Trailer> parseTrailerList(JSONArray trailerArray) throws JSONException {
        List<Trailer> trailers = new ArrayList<>();
        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailer = trailerArray.getJSONObject(i);
            Trailer currentTrailer = parseTrailer(trailer);
            trailers.add(currentTrailer);
        }
        return trailers;
    }

    @NonNull
    private static Trailer parseTrailer(JSONObject trailer) throws JSONException {
        Trailer currentTrailer = new Trailer();
        currentTrailer.setKey(trailer.getString(key));
        currentTrailer.setName(trailer.getString(name));
        currentTrailer.setSite(trailer.getString(site));
        currentTrailer.setType(trailer.getString(type));
        return currentTrailer;
    }
}