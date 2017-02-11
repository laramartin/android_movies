package eu.laramartin.popularmovies.data;

import java.util.List;

/**
 * Created by lara on 11.02.17.
 */

public class ReviewCollection {
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ReviewCollection{" +
                "reviews=" + reviews +
                '}';
    }
}
