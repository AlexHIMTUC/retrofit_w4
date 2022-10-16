package com.training.retrofit_w4.network

import com.training.retrofit_w4.model.Movie
import com.training.retrofit_w4.model.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface retrofitTemplate {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("api_key") apiKey : String, @Query("page") page : Int) : Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieByID(@Path("movie_id") movieId : Int, @Query("api_key") apiKey : String) : Response<MovieDetail>
}