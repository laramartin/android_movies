package eu.laramartin.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import eu.laramartin.popularmovies.data.Movie;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        Movie movie = data.getParcelable("movieDetails");
    }
}
