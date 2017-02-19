package eu.laramartin.popularmovies.ui;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import eu.laramartin.popularmovies.R;

/**
 * Created by lara on 19.02.17.
 */

public class FavoritesAdapter
        extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private Cursor cursor;

    public FavoritesAdapter() {
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public FavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO open details of movie
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
        // TODO
    }

    @Override
    public int getItemCount() {
        return 0;
    }





}
