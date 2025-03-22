package com.example.moviebuff.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.moviebuff.users.woker.SyncWorker
import javax.inject.Inject

class SyncHelper @Inject constructor(
    private val context: Context
){
    fun triggerOneTimeSync() {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
}