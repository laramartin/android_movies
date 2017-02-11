package eu.laramartin.popularmovies.data;

import java.util.List;

/**
 * Created by lara on 11.02.17.
 */

public class TrailerCollection {

    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "TrailerCollection{" +
                "trailers=" + trailers +
                '}';
    }
}
