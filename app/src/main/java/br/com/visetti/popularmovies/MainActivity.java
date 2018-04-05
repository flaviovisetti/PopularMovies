package br.com.visetti.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import br.com.visetti.popularmovies.adapter.MovieAdapter;
import br.com.visetti.popularmovies.model.Movie;
import br.com.visetti.popularmovies.utils.JsonUtils;
import br.com.visetti.popularmovies.utils.NetworkUtils;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private final static String POPULAR_OPTION = "popular";
    private final static String TOP_RATED = "top_rated";


    private RecyclerView mMovieDisplay;
    private MovieAdapter movieAdapter;
    private ProgressBar mProgressDisplay;
    private TextView mErrorDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieDisplay = findViewById(R.id.rv_movie_display);
        mProgressDisplay = findViewById(R.id.pb_progress_display);
        mErrorDisplay = findViewById(R.id.tv_error_message);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 4);
        mMovieDisplay.setLayoutManager(layoutManager);
        mMovieDisplay.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this);
        mMovieDisplay.setAdapter(movieAdapter);

        getResultFromNetwork(POPULAR_OPTION);

    }

    private void getResultFromNetwork(String queryOption) {
        if (NetworkUtils.isOnline(this)) {
            Request movieRequest = NetworkUtils.buildMovieUrl(queryOption);
            new MovieTask().execute(movieRequest);
        } else {
            showErrorDisplay();
        }
    }

    private void showErrorDisplay() {
        mMovieDisplay.setVisibility(View.INVISIBLE);
        mErrorDisplay.setVisibility(View.VISIBLE);
    }

    private void showDataDisplay() {
        mMovieDisplay.setVisibility(View.VISIBLE);
        mErrorDisplay.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(Movie itemMovie) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);

        bundle.putSerializable("movie", itemMovie);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private class MovieTask extends AsyncTask<Request, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDisplay.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Request... requests) {
            Request url = requests[0];
            String movieResults = null;
            try {

                movieResults = NetworkUtils.getNetworkResponse(url);

            } catch (IOException ex) {
                showErrorDisplay();
                ex.printStackTrace();
            }

            return movieResults;
        }

        @Override
        protected void onPostExecute(String movieData) {
            super.onPostExecute(movieData);
            mProgressDisplay.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                try {
                    showDataDisplay();
                    movieAdapter.setMovieList(JsonUtils.parseMovieJSON(movieData));
                } catch (JSONException ex) {
                    showErrorDisplay();
                    ex.printStackTrace();
                }
            } else {
                showErrorDisplay();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        switch (menuId) {
            case R.id.action_popular :
                movieAdapter = new MovieAdapter(this);
                mMovieDisplay.setAdapter(movieAdapter);
                getResultFromNetwork(POPULAR_OPTION);
                return true;

            case R.id.action_top_rated :
                movieAdapter = new MovieAdapter(this);
                mMovieDisplay.setAdapter(movieAdapter);
                getResultFromNetwork(TOP_RATED);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
