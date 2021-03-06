package br.com.visetti.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.visetti.popularmovies.R;
import br.com.visetti.popularmovies.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private List<Movie> movieList;
    private final MovieAdapterOnClickHandler onClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie itemMovie);
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public MovieAdapter(MovieAdapterOnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_movie_item, parent, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Picasso.get().load(movieList.get(position).getPosterPath()).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mMoviePoster;

        private MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie fetchMovie = movieList.get(position);
            onClickHandler.onClick(fetchMovie);
        }
    }
}
