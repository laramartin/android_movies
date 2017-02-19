package eu.laramartin.popularmovies.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.R;
import eu.laramartin.popularmovies.api.NetworkUtils;
import eu.laramartin.popularmovies.data.Movie;
import eu.laramartin.popularmovies.data.MoviesContract;

/**
 * Created by lara on 19.02.17.
 */

public class FavoritesAdapter
        extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private Cursor cursor;

    public FavoritesAdapter() {
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_poster)
        ImageView imagePoster;

        public FavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            cursor.moveToPosition(getAdapterPosition());
            Movie currentMovie = getMovieFromCursor();
            intent.putExtra("movieDetails", currentMovie);
            view.getContext().startActivity(intent);
        }

        @NonNull
        private Movie getMovieFromCursor() {
            Movie currentMovie = new Movie();
            currentMovie.setTitle(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE)));
            currentMovie.setId(cursor.getInt(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID)));
            currentMovie.setOverview(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_DESCRIPTION)));
            currentMovie.setPosterPath(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH)));
            currentMovie.setReleaseDate(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE)));
            currentMovie.setVoteAverage(cursor.getLong(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE)));
            return currentMovie;
        }
    }

    @Override
    public FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        FavoritesAdapterViewHolder viewHolder = new FavoritesAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesAdapterViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String posterPath = cursor.getString(
                cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH));
        Picasso.with(holder.imagePoster.getContext())
                .load(NetworkUtils.buildPosterUrl(posterPath))
                .placeholder(R.drawable.shape_movie_poster)
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }
}
