package br.com.visetti.popularmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.visetti.popularmovies.model.Movie;

public class JsonUtils {
    public static List<Movie> parseMovieJSON(String movieJSONObject) throws JSONException{
        JSONObject rawData = new JSONObject(movieJSONObject);
        JSONArray dataResults = rawData.getJSONArray("results");
        List<Movie> movies = new ArrayList<>();

        for(int i = 0; i < dataResults.length(); i++) {
            JSONObject movieJson = dataResults.getJSONObject(i);
            Movie movie = new Movie();

            movie.setTitle(movieJson.getString("title"));
            movie.setPosterPath(movieJson.getString("poster_path"));
            movie.setOverview(movieJson.getString("overview"));
            movie.setVoteAverage(movieJson.getString("vote_average"));
            movie.setReleaseData(movieJson.getString("release_date"));

            movies.add(movie);
        }

        return movies;
    }
}
