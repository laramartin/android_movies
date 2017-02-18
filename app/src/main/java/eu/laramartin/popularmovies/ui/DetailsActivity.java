package eu.laramartin.popularmovies.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.R;
import eu.laramartin.popularmovies.api.FetchReviewsTask;
import eu.laramartin.popularmovies.api.FetchTrailersTask;
import eu.laramartin.popularmovies.api.NetworkUtils;
import eu.laramartin.popularmovies.data.Movie;
import eu.laramartin.popularmovies.data.MoviesContract;
import eu.laramartin.popularmovies.data.MoviesDbHelper;
import eu.laramartin.popularmovies.data.Review;
import eu.laramartin.popularmovies.data.Trailer;

public class DetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
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

    @BindView(R.id.text_reviews_title)
    TextView textReviewsTitle;

    @BindView(R.id.layout_reviews_list)
    LinearLayout linearLayoutReviews;

    @BindView(R.id.text_trailer_title)
    TextView textTrailerTitle;

    @BindView(R.id.layout_trailers_list)
    LinearLayout linearLayoutTrailers;

    @BindView(R.id.favorite_image)
    ImageView imageFavorite;

    private MoviesDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        final Movie movie = data.getParcelable("movieDetails");
        setMovieDetails(movie);
        dbHelper = new MoviesDbHelper(this);
        imageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfMovieIsInDb(movie)) {
                    Log.v(LOG_TAG, "movie is in DB!!!");
                    // TODO fav icon changes to empty
                    showEmptyFavIcon();
                    // TODO delete movie into DB

                } else{
                    Log.v(LOG_TAG, "movie is not in DB...");
                    // TODO fav icon changes to filled
                     showFilledFavIcon();
                    // TODO save movie from DB
                }
            }
        });
        // TODO set on click listener on fav icon
            // TODO check if movie is stored in DB
                // TODO if movie is in DB, fav icon should be filled and movie saved to DB
                // TODO if movie is not in DB, fav icon should be empty and movie deleted from DB
    }

    private void showEmptyFavIcon() {
        imageFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    private void showFilledFavIcon() {
        imageFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

    private boolean checkIfMovieIsInDb(Movie movie) {
// TODO
        Cursor cursor = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int movieId = cursor.getInt(
                        cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID));
                if (movieId == movie.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setMovieDetails(Movie movie) {
        Picasso.with(imagePoster.getContext())
                .load(NetworkUtils.buildPosterUrl(movie.getPosterPath()))
                .placeholder(R.drawable.shape_movie_poster)
                .into(imagePoster);
        textDetailsTitle.setText(movie.getTitle());
        textDetailsReleaseDate.setText(
                String.format(
                        getResources().getString(R.string.release_date), movie.getReleaseDate()));
        textDetailsSynopsis.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage());
        new FetchTrailersTask(String.valueOf(movie.getId())) {
            @Override
            protected void onPostExecute(List<Trailer> trailers) {
                addTrailersToLayout(trailers);
            }
        }.execute();
        new FetchReviewsTask(String.valueOf(movie.getId())) {
            @Override
            protected void onPostExecute(List<Review> reviews) {
                addReviewsToLayout(reviews);
            }
        }.execute();

    }

    private void addTrailersToLayout(List<Trailer> trailers) {
        if (trailers != null && !trailers.isEmpty()) {
            for (Trailer trailer : trailers) {
                if (trailer.getType().equals("Trailer") &&
                        trailer.getSite().equals("YouTube")) {
                    View view = getTrailerView(trailer);
                    linearLayoutTrailers.addView(view);
                }
            }
        } else {
            hideTrailersSection();
        }
    }

    private void addReviewsToLayout(List<Review> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            for (Review review : reviews) {
                View view = getReviewView(review);
                linearLayoutReviews.addView(view);
            }
        } else {
            hideReviewsSection();
        }
    }

    private void hideTrailersSection() {
        textTrailerTitle.setVisibility(View.GONE);
        linearLayoutTrailers.setVisibility(View.GONE);
    }

    private void hideReviewsSection() {
        textReviewsTitle.setVisibility(View.GONE);
        linearLayoutReviews.setVisibility(View.GONE);
    }

    private View getTrailerView(final Trailer trailer) {
        LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.trailer_list_item, linearLayoutTrailers, false);
        TextView trailerNameTextView = ButterKnife.findById(view, R.id.text_trailer_item_name);
        trailerNameTextView.setText(trailer.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(NetworkUtils.buildYouTubeUrl(trailer.getKey())));
                startActivity(intent);
                Log.v("Details", "clicked play trailer: " + trailer.getName());
            }
        });
        return view;
    }

    private View getReviewView(final Review review) {
        LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.review_list_item, linearLayoutReviews, false);
        TextView contentTextView = ButterKnife.findById(view, R.id.text_content_review);
        TextView authorTextView = ButterKnife.findById(view, R.id.text_author_review);
        authorTextView.setText(getString(R.string.by_author_review, review.getAuthor()));
        contentTextView.setText(review.getContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = review.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        return view;
    }
}
