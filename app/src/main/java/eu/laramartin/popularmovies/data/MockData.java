package eu.laramartin.popularmovies.data;

/**
 * Created by lara on 27/1/17.
 */

public class MockData {

    public static Movie getSampleMovie() {
        Movie currentMovie = new Movie();
        currentMovie.setTitle("This is a sample title");
        currentMovie.setOverview("some long overview, aka synopsis blabla, I don't have wifi blabla I'm on a plane :)");
        currentMovie.setReleaseDate("2016-12-13");
        currentMovie.setVoteAverage(4);
        return currentMovie;
    }
}
