package com.example.moviebuff.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
@Preview
fun UserListItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        AsyncImage(
            modifier = Modifier
                .clickable {}
                .size(64.dp)
                .clip(RoundedCornerShape(100.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    ""
                )
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.profile),
            placeholder = painterResource(id = R.drawable.profile),
            contentDescription = "profilepic",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Users",
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        )
    }
}