package com.example.moviebuff.movies

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviebuff.R
import com.example.moviebuff.utils.SharedViewModel
import com.example.moviebuff.movies.viewmodel.MoviesViewModel
import com.example.moviebuff.ui.theme.fontFamily
import com.example.moviebuff.users.ErrorItem
import com.example.moviebuff.users.LoadingIndicator
import com.example.moviebuff.utils.NetworkResult

@Composable
fun MovieDetailsScreen(
    sharedViewModel: SharedViewModel,
    moviesViewModel: MoviesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        sharedViewModel.selectedMovie?.let {
            Log.d("TAG", it.id.toString())
            moviesViewModel.getMovieDetails(it.id)
        }
    }
    val movieDetail = moviesViewModel.movieDetails.collectAsState()
    when(movieDetail.value){
        is NetworkResult.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ){
                ErrorItem(movieDetail.value.msg.toString())
            }
        }
        is NetworkResult.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ){
                LoadingIndicator("Loading...")
            }
        }
        is NetworkResult.Success -> {
            movieDetail.value.data?.let {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                    ){
                        Box(
                            modifier = Modifier.align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .background(brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF00BCD4), Color(0xFF8BC34A))
                                ))
                                .height(200.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 75.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(225.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        "https://image.tmdb.org/t/p/w185/${it.poster_path}"
                                    )
                                    .crossfade(true)
                                    .build(),
                                error = painterResource(id = R.drawable.default_placeholder),
                                placeholder = painterResource(id = R.drawable.default_placeholder),
                                contentDescription = "movie_poster",
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Title",
                            fontSize = 16.sp,
                            color = Color(0xff6c757d),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = it.original_title,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            modifier = Modifier.padding(top = 20.dp),
                            text = "Description",
                            fontSize = 16.sp,
                            color = Color(0xff6c757d),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = it.overview,
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.padding(top = 20.dp),
                            text = "Release Date",
                            fontSize = 16.sp,
                            color = Color(0xff6c757d),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = it.release_date,
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        else -> {}
    }

}