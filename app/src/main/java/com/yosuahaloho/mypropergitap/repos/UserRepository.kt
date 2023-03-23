package com.yosuahaloho.mypropergitap.repos

import android.util.Log
import com.yosuahaloho.mypropergitap.repos.local.dao.FavoriteUserDao
import com.yosuahaloho.mypropergitap.repos.remote.ApiService
import kotlinx.coroutines.flow.flow
import com.yosuahaloho.mypropergitap.utils.Result

class UserRepository private constructor(private val apiService: ApiService, private val favoriteUserDao: FavoriteUserDao) {

    fun getDefaultUser() = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getDefaultUsers()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailUser(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getSearchUser(query: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.searchUser(query)
            emit(Result.Success(response.items))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favoriteUserDao)
            }.also {
                instance = it
            }
    }
}