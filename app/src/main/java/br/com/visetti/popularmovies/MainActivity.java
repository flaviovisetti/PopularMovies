package br.com.visetti.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import br.com.visetti.popularmovies.utils.NetworkUtil;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private TextView mExampleData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExampleData = (TextView) findViewById(R.id.tv_example_data);
        Request movieRequest = NetworkUtil.buildMovieUrl("popular");
        new MovieTask().execute(movieRequest);
    }

    public class MovieTask extends AsyncTask<Request, Void, String> {

        @Override
        protected String doInBackground(Request... requests) {
            Request url = requests[0];
            String movieResults = null;
            try {

                movieResults = NetworkUtil.getNetworkResponse(url);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return movieResults;
        }

        @Override
        protected void onPostExecute(String movieData) {
            super.onPostExecute(movieData);
            if (movieData != null) {
                mExampleData.setText(movieData);
            }
        }
    }
}
