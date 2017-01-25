package eu.laramartin.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.net.URL;
import java.util.List;

import eu.laramartin.popularmovies.api.MoviesJsonUtils;
import eu.laramartin.popularmovies.api.NetworkUtils;
import eu.laramartin.popularmovies.data.Movie;
import eu.laramartin.popularmovies.data.MoviesResponse;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new MoviesAdapter();
        recyclerView.setAdapter(adapter);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            URL moviesRequestUrl = NetworkUtils.buildUrl(BuildConfig.API_KEY);
            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);
                MoviesResponse moviesResponse = MoviesJsonUtils.parseJson(jsonMoviesResponse);
                return moviesResponse.getMovies();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                Log.v(LOG_TAG, "moviesData: " + movies);
                adapter.setMoviesData(movies);
            }
        }
    }
}
