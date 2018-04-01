package br.com.visetti.popularmovies;

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
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.com.visetti.popularmovies.adapter.MovieAdapter;
import br.com.visetti.popularmovies.utils.JsonUtils;
import br.com.visetti.popularmovies.utils.NetworkUtils;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private final String mostPopularOption = "popular";
    private final String topRatedOption = "top_rated";


    private RecyclerView mMovieDisplay;
    private MovieAdapter movieAdapter;
    private Toast mToast;
    private ProgressBar mProgressDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieDisplay = (RecyclerView) findViewById(R.id.rv_movie_display);
        mProgressDisplay = (ProgressBar) findViewById(R.id.pb_progress_display);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 4);
        mMovieDisplay.setLayoutManager(layoutManager);
        mMovieDisplay.setHasFixedSize(true);

        movieAdapter = new MovieAdapter();
        mMovieDisplay.setAdapter(movieAdapter);

        showMoviesResults(mostPopularOption);

    }

    private void showMoviesResults(String queryOption) {
        Request movieRequest = NetworkUtils.buildMovieUrl(queryOption);
        new MovieTask().execute(movieRequest);
    }

    public class MovieTask extends AsyncTask<Request, Void, String> {
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
                    movieAdapter.setMovieList(JsonUtils.parseMovieJSON(movieData));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

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
                movieAdapter = new MovieAdapter();
                mMovieDisplay.setAdapter(movieAdapter);
                showMoviesResults(mostPopularOption);
                return true;

            case R.id.action_top_rated :
                movieAdapter = new MovieAdapter();
                mMovieDisplay.setAdapter(movieAdapter);
                showMoviesResults(topRatedOption);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMessage(String message) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
