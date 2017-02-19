package eu.laramartin.popularmovies.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.R;
import eu.laramartin.popularmovies.api.FetchMoviesTask;
import eu.laramartin.popularmovies.data.FavoritesCursorLoader;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private MoviesAdapter moviesAdapter;
    private int selectedOption = R.id.action_popular;
    private static final String FILTER_TYPE_1 = "popular";
    private static final String FILTER_TYPE_2 = "top_rated";
    public static final int ID_FAVORITES_LOADER = 11;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, getSpan());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        if (savedInstanceState == null) {
            setMovieAdapterPopular();
        } else {
            loadAdapterPerOptionSelected(savedInstanceState.getInt("optionSelected", R.id.action_popular));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("optionSelected", selectedOption);
    }

    private int getSpan() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 4;
        }
        return 2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadAdapterPerOptionSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void loadAdapterPerOptionSelected(int selectedOption) {
        this.selectedOption = selectedOption;
        if (selectedOption == R.id.action_popular) {
            setMovieAdapterPopular();
        }
        if (selectedOption == R.id.action_top_rated) {
            setMovieAdapterTopRated();
        }
        if (selectedOption == R.id.action_favorites) {
            setMovieAdapterFavorites();
        }
    }

    private void setMovieAdapterFavorites() {
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter();
        recyclerView.setAdapter(favoritesAdapter);
        getSupportLoaderManager().initLoader(
                ID_FAVORITES_LOADER, null, new FavoritesCursorLoader(this, favoritesAdapter));
    }

    private void setMovieAdapterTopRated() {
        moviesAdapter = new MoviesAdapter();
        FetchMoviesTask moviesTask = new FetchMoviesTask(moviesAdapter);
        recyclerView.setAdapter(moviesAdapter);
        moviesTask.execute(FILTER_TYPE_2);
    }

    private void setMovieAdapterPopular() {
        moviesAdapter = new MoviesAdapter();
        FetchMoviesTask moviesTask = new FetchMoviesTask(moviesAdapter);
        recyclerView.setAdapter(moviesAdapter);
        moviesTask.execute(FILTER_TYPE_1);
    }
}