package com.example.moviebuff.models.movie_models

data class GetMovieDetailsResponse(
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
)