package com.example.moviebuff.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviebuff.models.movie_models.GetMovieDetailsResponse
import com.example.moviebuff.movies.repository.MoviesRepository
import com.example.moviebuff.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository): ViewModel() {
    val movies = repository.getMovies().cachedIn(viewModelScope)

    val movieDetails: StateFlow<NetworkResult<GetMovieDetailsResponse>> get()  = repository.movieDetailsState

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId)
        }
    }
}