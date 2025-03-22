package com.example.moviebuff.users.woker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moviebuff.users.respository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: UserRepository
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        try{
            Log.d("SyncWorker", "Syncing users...")
            repository.syncOfflineUsers()
            return Result.success()
        }catch (e: Exception) {
            Log.e("SyncWorker", "Error during background sync", e)
            return Result.failure()
        }

    }
}
