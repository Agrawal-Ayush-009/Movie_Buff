package com.example.moviebuff.utils

import androidx.lifecycle.ViewModel
import com.example.moviebuff.models.movie_models.Movie

class SharedViewModel: ViewModel() {
    private var _selectedMovie : Movie? = null
    val selectedMovie : Movie? get() = _selectedMovie

    fun setSelectedMovie(movie: Movie){
        _selectedMovie = movie
    }
}