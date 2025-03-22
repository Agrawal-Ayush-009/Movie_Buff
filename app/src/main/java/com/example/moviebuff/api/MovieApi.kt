package com.example.moviebuff.api

import com.example.moviebuff.models.movie_models.GetMovieDetailsResponse
import com.example.moviebuff.models.movie_models.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/day")
    suspend fun getMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "ae1554c684a97ad1792631c37e6bb739"
    ): GetMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") language: String = "ae1554c684a97ad1792631c37e6bb739",
    ): Response<GetMovieDetailsResponse>
}