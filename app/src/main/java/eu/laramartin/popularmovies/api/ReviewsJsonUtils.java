package eu.laramartin.popularmovies.api;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.laramartin.popularmovies.data.Review;
import eu.laramartin.popularmovies.data.ReviewCollection;

/**
 * Created by lara on 11.02.17.
 */
public class ReviewsJsonUtils {

    private static final String LOG_TAG = ReviewsJsonUtils.class.getCanonicalName();
    private static final String statusError = "status_code";
    private static final String reviews = "results";
    private static final String author = "author";
    private static final String content = "content";
    private static final String url = "url";

    public static ReviewCollection parseJson(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(statusError)) {
            int errorCode = responseJson.getInt(statusError);
            Log.e(LOG_TAG, "parse json reviews error code: " + String.valueOf(errorCode));
        }
        JSONArray reviewsArray = responseJson.getJSONArray(reviews);
        List<Review> reviewList = parseReviewList(reviewsArray);
        ReviewCollection reviewCollection = new ReviewCollection();
        reviewCollection.setReviews(reviewList);
        return reviewCollection;
    }

    @NonNull
    private static List<Review> parseReviewList(JSONArray reviewArray) throws JSONException {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);
            Review currentReview = parseReview(review);
            reviews.add(currentReview);
        }
        return reviews;
    }

    @NonNull
    private static Review parseReview(JSONObject review) throws JSONException {
        Review currentReview = new Review();
        currentReview.setAuthor(review.getString(author));
        currentReview.setContent(review.getString(content));
        currentReview.setUrl(review.getString(url));
        return currentReview;
    }
}