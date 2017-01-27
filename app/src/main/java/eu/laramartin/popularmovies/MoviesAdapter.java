package eu.laramartin.popularmovies;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.data.Movie;

/**
 * Created by lara on 25/1/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    public MoviesAdapter() {
    }

    public void setMoviesData(List<Movie> list) {
        movies = list;
        notifyDataSetChanged();
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_poster) ImageView imagePoster;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            Movie currentMovie = movies.get(getAdapterPosition());
            intent.putExtra("movieDetails", currentMovie);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        MoviesAdapterViewHolder viewHolder = new MoviesAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
//        Picasso.with(holder.imagePoster.getContext())
//                .load(NetworkUtils.buildPosterUrl(movies.get(position).getPosterPath()))
//                .into(holder.imagePoster);
        holder.imagePoster.setImageResource(R.drawable.ic_android_black_24dp);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
