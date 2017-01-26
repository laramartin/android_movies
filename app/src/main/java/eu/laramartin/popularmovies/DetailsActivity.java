package eu.laramartin.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.api.NetworkUtils;
import eu.laramartin.popularmovies.data.Movie;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.image_details_poster)
    ImageView imagePoster;

    @BindView(R.id.text_details_title)
    TextView textDetailsTitle;

    @BindView(R.id.text_details_release_date)
    TextView textDetailsReleaseDate;

    @BindView(R.id.text_details_vote_average)
    TextView textDetailsVoteAverage;

    @BindView(R.id.text_details_synopsis)
    TextView textDetailsSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        Movie movie = data.getParcelable("movieDetails");
        setMovieDetails(movie);
    }

    private void setMovieDetails(Movie movie) {
        Picasso.with(imagePoster.getContext())
                .load(NetworkUtils.buildPosterUrl(movie.getPosterPath()))
                .into(imagePoster);
        textDetailsTitle.setText(movie.getTitle());
        textDetailsReleaseDate.setText(movie.getReleaseDate());
        textDetailsVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        textDetailsSynopsis.setText(movie.getOverview());
    }
}
