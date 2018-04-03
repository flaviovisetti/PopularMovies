package br.com.visetti.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.visetti.popularmovies.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    private final static String TAG = MovieDetailActivity.class.getSimpleName();
    private ImageView mPosterImage;
    private TextView mTitleDetail;
    private TextView mOverview;
    private TextView mUserRating;
    private TextView mReleaseDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail);

        mPosterImage = (ImageView) findViewById(R.id.iv_detail_poster);
        mTitleDetail = (TextView) findViewById(R.id.tv_detail_title);
        mOverview = (TextView) findViewById(R.id.tv_detail_overview);
        mUserRating = (TextView) findViewById(R.id.tv_detail_rating);
        mReleaseDate = (TextView) findViewById(R.id.tv_detail_release_date);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Movie movie = (Movie) bundle.getSerializable("movie");
        Picasso.get().load(movie.getPosterPath()).into(mPosterImage);
        mTitleDetail.setText(movie.getTitle());
        mOverview.setText(movie.getOverview());

        Log.d(TAG, movie.getVoteAverage());
        Log.d(TAG, movie.getReleaseData());

        mUserRating.setText(movie.getVoteAverage());
        mReleaseDate.setText(movie.getReleaseData());
    }
}
