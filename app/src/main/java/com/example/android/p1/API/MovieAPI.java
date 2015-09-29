package com.example.android.p1.API;

import com.example.android.p1.model.PopularMovies;
import com.example.android.p1.model.TMDBMovie;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by dave_ on 9/9/2015.
 */
public interface MovieAPI {
    @GET("/discover/movie")
    void getMovies(@Query("sort_by") String sortParam, @Query("api_key") String apiKey, Callback<PopularMovies> cb);

    @GET("/movie/{id}")
    void getMovie(@Path("id") String id, @Query("api_key") String apiKey, Callback<TMDBMovie> cb);
}
