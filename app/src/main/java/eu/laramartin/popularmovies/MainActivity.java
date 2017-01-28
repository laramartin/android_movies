package eu.laramartin.popularmovies;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.api.MoviesJsonUtils;
import eu.laramartin.popularmovies.api.NetworkUtils;
import eu.laramartin.popularmovies.data.Movie;
import eu.laramartin.popularmovies.data.MoviesResponse;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private MoviesAdapter adapter;
    private static final String FILTER_TYPE_1 = "popular";
    private static final String FILTER_TYPE_2 = "top_rated";

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute(FILTER_TYPE_1);
        GridLayoutManager layoutManager = new GridLayoutManager(this, getSpan());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new MoviesAdapter();
        recyclerView.setAdapter(adapter);
    }

    private int getSpan() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 4;
        }
        return 2;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            URL moviesRequestUrl = NetworkUtils.buildUrl(BuildConfig.API_KEY, params[0]);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_popular) {
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute(FILTER_TYPE_1);
        }
        if (item.getItemId() == R.id.action_top_rated) {
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute(FILTER_TYPE_2);
        }
        return super.onOptionsItemSelected(item);
    }
}