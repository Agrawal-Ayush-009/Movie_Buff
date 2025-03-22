package com.example.moviebuff.users.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviebuff.api.UserApi
import com.example.moviebuff.models.user_models.UserData

class UserPagingSource(private val api: UserApi) :
    PagingSource<Int, UserData>() {

    override fun getRefreshKey(state: PagingState<Int, UserData>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                anchor
            )?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserData> {
        try {
            val page = params.key ?: 1
            val response = api.getUsers(page)
            Log.d("TAG", response.toString())
            val userList = response.data
            val endReached = userList.isEmpty()

            return LoadResult.Page(
                data = userList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (endReached) null else page + 1
            )
        } catch (e: Exception) {
            Log.d("TAG", "error in paging $e")
            return LoadResult.Error(e)
        }
    }
}