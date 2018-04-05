package br.com.visetti.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.visetti.popularmovies.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail);

        ImageView mPosterImage = findViewById(R.id.iv_detail_poster);
        TextView mTitleDetail = findViewById(R.id.tv_detail_title);
        TextView mOverview = findViewById(R.id.tv_detail_overview);
        TextView mUserRating = findViewById(R.id.tv_detail_rating);
        TextView mReleaseDate = findViewById(R.id.tv_detail_release_date);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Movie movie = (Movie) bundle.getSerializable("movie");
        Picasso.get().load(movie.getPosterPath()).into(mPosterImage);
        mTitleDetail.setText(movie.getTitle());
        mOverview.setText(movie.getOverview());

        mUserRating.setText(movie.getVoteAverage());
        mReleaseDate.setText(movie.getReleaseData());
    }
}
