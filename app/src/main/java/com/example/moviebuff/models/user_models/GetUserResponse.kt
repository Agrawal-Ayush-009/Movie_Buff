package com.example.moviebuff.models.user_models

data class GetUserResponse(
    val `data`: List<UserData>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)

data class Support(
    val text: String,
    val url: String
)

data class UserData(
    val avatar: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String
)