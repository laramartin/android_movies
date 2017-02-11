package eu.laramartin.popularmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.R;
import eu.laramartin.popularmovies.api.NetworkUtils;
import eu.laramartin.popularmovies.data.Movie;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.image_details_poster)
    ImageView imagePoster;

    @BindView(R.id.text_details_title)
    TextView textDetailsTitle;

    @BindView(R.id.text_details_release_date)
    TextView textDetailsReleaseDate;

    @BindView(R.id.text_details_synopsis)
    TextView textDetailsSynopsis;

    @BindView(R.id.rating_bar)
    RatingBar ratingBar;

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
        textDetailsReleaseDate.setText(
                String.format(
                        getResources().getString(R.string.release_date), movie.getReleaseDate()));
        textDetailsSynopsis.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage());
    }
}
