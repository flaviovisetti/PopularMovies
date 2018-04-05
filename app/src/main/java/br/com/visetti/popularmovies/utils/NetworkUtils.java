package br.com.visetti.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    private final static String API_ENDPOINT = "http://api.themoviedb.org/3/movie/";
    private final static String API_KEY = "";

    public static Request buildMovieUrl(String queryType) {
        Uri uri = Uri.parse(API_ENDPOINT + queryType).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();

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

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
