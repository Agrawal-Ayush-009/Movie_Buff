package com.example.moviebuff.movies.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviebuff.api.MovieApi
import com.example.moviebuff.models.movie_models.Movie

class MoviesPagingSource(private val api: MovieApi): PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            Log.d("TAG", params.key.toString())
            val response = api.getMovies(
                page = page
            )
            val moviesList = response.results
            LoadResult.Page(
                data = moviesList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.total_pages) page + 1 else null
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}