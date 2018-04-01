package br.com.visetti.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtil {
    private final static String TAG = NetworkUtil.class.getSimpleName();
    private final static String API_ENDPOINT = "http://api.themoviedb.org/3/movie/";
    private final static String API_KEY = "";

    public static Request buildMovieUrl(String queryType) {
        Uri uri = Uri.parse(API_ENDPOINT + queryType).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();
        Log.d(TAG, uri.toString());

        Request request = new Request.Builder().url(uri.toString())
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }

    
    public static String getNetworkResponse(Request url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(url).execute();
        String jsonData = null;

        if (response.code() == 200) {
            jsonData = response.body().string();
        }

        return jsonData;
    }
}
