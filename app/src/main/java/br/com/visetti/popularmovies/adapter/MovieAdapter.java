package br.com.visetti.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.visetti.popularmovies.R;
import br.com.visetti.popularmovies.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private List<Movie> movieList;
    private MovieAdapterOnClickHandler onClickHandler;

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

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_movie_item, parent, false);
        MovieAdapterViewHolder viewHolder = new MovieAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Picasso.get().load(movieList.get(position).getPosterPath()).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mMoviePoster;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int postion = getAdapterPosition();
            Movie fetchMovie = movieList.get(postion);
            onClickHandler.onClick(fetchMovie);
        }
    }
}
