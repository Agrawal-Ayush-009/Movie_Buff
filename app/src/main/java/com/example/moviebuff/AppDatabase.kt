package com.example.moviebuff

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviebuff.users.roomDB.UserDao
import com.example.moviebuff.users.roomDB.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}