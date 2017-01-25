package eu.laramartin.popularmovies.data;

import java.util.List;

/**
 * Created by lara on 24/1/17.
 */

public class MoviesResponse {

    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MoviesResponse{" +
                "movies=" + movies +
                '}';
    }
}
