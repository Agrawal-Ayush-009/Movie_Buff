package com.example.moviebuff.users.respository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviebuff.api.UserApi
import com.example.moviebuff.models.user_models.AddUserRequest
import com.example.moviebuff.models.user_models.AddUserResponse
import com.example.moviebuff.models.user_models.UserData
import com.example.moviebuff.users.paging.UserPagingSource
import com.example.moviebuff.users.roomDB.UserDao
import com.example.moviebuff.users.roomDB.UserEntity
import com.example.moviebuff.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.SocketTimeoutException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi,
    private val userDao: UserDao
) {
    private val _addUserState =
        MutableStateFlow<NetworkResult<AddUserResponse>>(NetworkResult.Start())
    val addUserState: StateFlow<NetworkResult<AddUserResponse>> get() = _addUserState


    fun getUsers(): Pager<Int, UserData> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                UserPagingSource(api)
            }
        )
    }

    suspend fun addUser(userEntity: UserEntity, isOnline: Boolean) {
        _addUserState.value = NetworkResult.Loading()
        if (isOnline) {
            try {
                val response = api.addUser(
                    AddUserRequest(
                        userEntity.name,
                        userEntity.job,
                    )
                )
                if (response.isSuccessful || response.body() != null) {
                    _addUserState.value = NetworkResult.Success(response.body())
                    Log.d("SyncWorker", "User posted remotely")
                } else {
                    _addUserState.value = NetworkResult.Error("Something went wrong")
                }
            } catch (e: SocketTimeoutException) {
                _addUserState.value = NetworkResult.Error("Check your Network Connection")
            } catch (e: Exception) {
                _addUserState.value = NetworkResult.Error("Unexpected Error Occurred")
            }
        } else {
            try {
                userDao.insertUser(userEntity.copy(isSynced = false))
                _addUserState.value = NetworkResult.Success(null)
                Log.d("SyncWorker", "User posted locally")
            } catch (e: Exception) {
                _addUserState.value = NetworkResult.Error("Unexpected Error Occurred")
            }
        }
    }

    suspend fun syncOfflineUsers() {
        val unsyncedUsers = userDao.getUnsyncedUsers()
        unsyncedUsers.forEach{user->
            try {
                val response = api.addUser(
                    AddUserRequest(
                        user.name,
                        user.job,
                    )
                )
                if(response.isSuccessful){
                    userDao.updateUser(user.copy(isSynced = true))
                }else{
                    _addUserState.value = NetworkResult.Error("Something went wrong")
                }
            }catch (e: Exception) {
                _addUserState.value = NetworkResult.Error("Unexpected Error Occurred")
            }
            userDao.deleteSyncedUsers()
        }
    }

    fun removeAddUserState() {
        _addUserState.value = NetworkResult.Start()
    }
}