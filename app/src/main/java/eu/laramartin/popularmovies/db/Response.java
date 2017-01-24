package eu.laramartin.popularmovies.db;

import java.util.List;

/**
 * Created by lara on 24/1/17.
 */

public class Response {

    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
