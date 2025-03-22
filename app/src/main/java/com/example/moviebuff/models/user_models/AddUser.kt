package com.example.moviebuff.models.user_models

data class AddUserRequest(
    val job: String,
    val name: String
)

data class AddUserResponse(
    val createdAt: String,
    val id: String,
    val job: String,
    val name: String
)
