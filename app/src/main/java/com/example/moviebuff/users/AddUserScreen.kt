package com.example.moviebuff.users

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviebuff.R
import com.example.moviebuff.SharedViewModel
import com.example.moviebuff.ui.theme.fontFamily
import com.example.moviebuff.users.roomDB.UserEntity
import com.example.moviebuff.users.viewmodel.UserViewmodel
import com.example.moviebuff.utils.NetworkResult

@Composable
fun AddUserScreen(
    backOnclick: () -> Unit,
    viewModel: UserViewmodel = hiltViewModel()
) {
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }

    var job by remember {
        mutableStateOf("")
    }
    val addUserState = viewModel.addUserState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        backOnclick()
                    },
                painter = painterResource(R.drawable.arrow_ios_back_svgrepo_com),
                contentDescription = "backArrow"
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "Add User",
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp),

        ) {
            Text(
                text = "Enter Full Name:",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                border = BorderStroke(1.dp, Color(0xFF999999)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                BasicTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 1.5.em
                    ),
                    cursorBrush = SolidColor(Color(0xFF0096c7)),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    decorationBox = { innerTextField ->
                        if (name.isEmpty()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Name",
                                    color = Color(0xFF999999),
                                    fontFamily = fontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                        innerTextField()
                    },
                    singleLine = true
                )
            }

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Enter Job:",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                border = BorderStroke(1.dp, Color(0xFF999999)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                BasicTextField(
                    value = job,
                    onValueChange = {
                        job = it
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 1.5.em
                    ),
                    cursorBrush = SolidColor(Color(0xFF0096c7)),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    decorationBox = { innerTextField ->
                        if (job.isEmpty()) {
                            Text(
                                text = "Job",
                                color = Color(0xFF999999),
                                fontFamily = fontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        innerTextField()
                    },
                    singleLine = true
                )
            }
            when(addUserState.value){
                is NetworkResult.Error -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ErrorItem(addUserState.value.msg.toString())
                    }
                }
                is NetworkResult.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LoadingIndicator("Adding User...")
                    }
                }
                is NetworkResult.Success -> {
                    Toast.makeText(context, "User Added Successfully", Toast.LENGTH_SHORT).show()
                    backOnclick()
                    viewModel.removeAddUserState()
                }
                else -> {}
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                onClick = {
                    viewModel.addUser(
                        UserEntity(
                            name = name,
                            job = job
                        )
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0096c7),
                    contentColor = Color.White,
                    disabledBackgroundColor = Color(0xFF5E5E5E),
                    disabledContentColor = Color(0xFF66FFFFFF)
                ),
                enabled = name.isNotEmpty() && job.isNotEmpty()

            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "Add",
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    color = if(name.isNotEmpty() && job.isNotEmpty()) Color.Black else Color(0xFFFFFFFF)
                )
            }
        }
    }
}