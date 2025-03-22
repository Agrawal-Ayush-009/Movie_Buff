package com.example.moviebuff.movies

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviebuff.SharedViewModel
import com.example.moviebuff.movies.viewmodel.MoviesViewModel
import com.example.moviebuff.ui.theme.fontFamily
import com.example.moviebuff.users.ErrorItem
import com.example.moviebuff.users.LoadingIndicator

@Composable
fun MoviesListScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onMovieClick: () -> Unit
){
    val moviesList = viewModel.movies.collectAsLazyPagingItems()
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Movies",
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(moviesList.itemCount){index->
                val movie = moviesList[index]

                movie?.let{
                    MovieListItem(
                        title = it.title,
                        posterUrl = it.poster_path.toString(),
                        releaseDate = it.release_date,
                        onClick = {
                            sharedViewModel.setSelectedMovie(it)
                            Log.d("TAG", it.id.toString())
                            onMovieClick()
                        }
                    )
                }
            }

            moviesList.apply {
                when (loadState.refresh) {
                    is LoadState.Loading -> item { LoadingIndicator("Loading...") }
                    is LoadState.Error -> item { ErrorItem("Error: ${(loadState.refresh as LoadState.Error).error.localizedMessage}") }
                    else -> {}
                }
                when (loadState.append) {
                    is LoadState.Loading -> item { LoadingIndicator("Loading more...") }
                    is LoadState.Error -> item { ErrorItem("Pagination Error!") }
                    else -> {}
                }
            }
        }
    }
}