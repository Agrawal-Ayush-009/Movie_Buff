package com.example.moviebuff.users

import android.os.Message
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviebuff.R
import com.example.moviebuff.ui.theme.fontFamily
import com.example.moviebuff.users.viewmodel.UserViewmodel

@Composable
fun UserListScreen(
    viewmodel: UserViewmodel = hiltViewModel(),
    userOnClick: () -> Unit,
    addUserOnclick: () -> Unit
) {
    val userList = viewmodel.userList.collectAsLazyPagingItems()

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.ios_menu_4),
                    contentDescription = "menu"
                )
                Text(
                    text = "Users",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .size(32.dp),
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "profile"
                )
            }
            LazyColumn {
                items(userList.itemCount){ index ->
                    val user = userList[index]
                    user?.let {
                        UserListItem(
                            name = "${ it.first_name } ${it.last_name}",
                            imgUrl = it.avatar,
                            onClick = {
                                userOnClick()
                            }
                        )
                    }
                }
                userList.apply {
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
        FloatingActionButton(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                addUserOnclick()
            },
            containerColor = Color(0xFF0096c7),
            shape = RoundedCornerShape(32.dp),
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun LoadingIndicator(message: String) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = Color(0xFF0096c7)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message)
        }
    }
}

@Composable
fun ErrorItem(message: String) {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
        Text(text = "Error: $message", color = Color.Red)
    }
}