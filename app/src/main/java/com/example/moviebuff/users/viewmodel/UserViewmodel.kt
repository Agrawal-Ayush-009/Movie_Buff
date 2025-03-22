package com.example.moviebuff.users.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.moviebuff.users.woker.SyncWorker
import com.example.moviebuff.users.respository.UserRepository
import com.example.moviebuff.users.roomDB.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(
    private val repository: UserRepository,
    private val app: Application
): ViewModel() {
    var userList = repository.getUsers().flow.cachedIn(viewModelScope)
    val addUserState get() = repository.addUserState

    init {
        scheduleSyncWork(app)
    }

    fun addUser(userEntity: UserEntity) {
        viewModelScope.launch{
            val isOnline = isOnline(app)
            repository.addUser(userEntity, isOnline)
            scheduleSyncWork(app)
        }
    }

    fun removeAddUserState(){
        repository.removeAddUserState()
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected == true
    }

    fun scheduleSyncWork(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "sync_users",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}
