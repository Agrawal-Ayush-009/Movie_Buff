package com.example.moviebuff.api

import com.example.moviebuff.models.user_models.AddUserRequest
import com.example.moviebuff.models.user_models.AddUserResponse
import com.example.moviebuff.models.user_models.GetUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): GetUserResponse

    @POST("users")
    suspend fun addUser(
        @Body addUserRequest: AddUserRequest
    ): Response<AddUserResponse>
}