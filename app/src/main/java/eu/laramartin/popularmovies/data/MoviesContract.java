package eu.laramartin.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by lara on 18.02.17.
 */

public class MoviesContract {

    private MoviesContract() {
    }

    public final class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_DESCRIPTION = "movieDescription";
        public static final String COLUMN_MOVIE_POSTER_PATH = "moviePosterPath";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movieVoteAverage";

        public static final String CREATE_TABLE_MOVIES = "CREATE TABLE " +
                MoviesEntry.TABLE_NAME + "(" +
                MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                MoviesEntry.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL," +
                MoviesEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL," +
                MoviesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE + " LONG NOT NULL," +
                ");";
    }
}
