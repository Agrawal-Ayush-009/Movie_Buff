package com.example.moviebuff.movies.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviebuff.api.MovieApi
import com.example.moviebuff.models.movie_models.GetMovieDetailsResponse
import com.example.moviebuff.models.movie_models.Movie
import com.example.moviebuff.movies.paging.MoviesPagingSource
import com.example.moviebuff.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.SocketTimeoutException
import javax.inject.Inject

class MoviesRepository@Inject constructor(private val api: MovieApi) {
    private var _movieDetailsState = MutableStateFlow<NetworkResult<GetMovieDetailsResponse>>(NetworkResult.Start())
    val movieDetailsState: StateFlow<NetworkResult<GetMovieDetailsResponse>> = _movieDetailsState

    fun getMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = { MoviesPagingSource(api) }
    ).flow

    suspend fun getMovieDetails(movieId: Int) {
        _movieDetailsState.value = NetworkResult.Loading()
        try{
            val response = api.getMovieDetails(movieId)
            Log.d("TAG", response.body().toString())
            if(response.isSuccessful || response.body() != null){
                _movieDetailsState.value = NetworkResult.Success(response.body())
            }else{
                _movieDetailsState.value = NetworkResult.Error("Something went wrong")
            }
        }catch (e: SocketTimeoutException) {
            _movieDetailsState.value = NetworkResult.Error("Check your Network Connection")
        } catch (e: Exception) {
            _movieDetailsState.value = NetworkResult.Error("Unexpected Error Occurred")
        }
    }
}