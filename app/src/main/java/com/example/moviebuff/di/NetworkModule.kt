package com.example.moviebuff.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.room.Room
import androidx.work.WorkerFactory
import com.example.moviebuff.AppDatabase
import com.example.moviebuff.api.MovieApi
import com.example.moviebuff.api.UserApi
import com.example.moviebuff.users.roomDB.UserDao
import com.example.moviebuff.utils.Constants.MOVIE_BASE_URL
import com.example.moviebuff.utils.Constants.USER_BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)  // Connection timeout
        .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
        .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
        .build()

    @Singleton
    @Provides
    fun provideRetrofitForUser(): Builder {
        return Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(USER_BASE_URL)

    }

    @Singleton
    @Provides
    @Named("MoviesRetrofit")
    fun providesRetrofitForTournament(): Builder{
        return Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MOVIE_BASE_URL)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun providesUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }


    @Singleton
    @Provides
    fun providesMoviesApi(@Named("MoviesRetrofit") retrofitBuilder: Builder): MovieApi{
        return retrofitBuilder.build().create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun providesUserApi(retrofitBuilder: Builder): UserApi{
        return retrofitBuilder.build().create(UserApi::class.java)
    }
}