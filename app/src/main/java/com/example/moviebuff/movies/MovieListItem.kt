package com.example.moviebuff.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviebuff.R
import com.example.moviebuff.ui.theme.fontFamily

@Composable
fun MovieListItem(
    title: String,
    posterUrl: String,
    releaseDate: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clickable {}
                .width(100.dp)
                .height(150.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    "https://image.tmdb.org/t/p/w185/$posterUrl"
                )
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.default_placeholder),
            placeholder = painterResource(id = R.drawable.default_placeholder),
            contentDescription = "profilepic",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Movie",
                fontSize = 14.sp,
                color = Color(0xff6c757d),
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = title,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                text = "Release Date",
                fontSize = 14.sp,
                color = Color(0xff6c757d),
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = releaseDate,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}