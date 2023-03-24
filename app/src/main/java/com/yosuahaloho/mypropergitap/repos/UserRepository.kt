package com.yosuahaloho.mypropergitap.repos

import com.yosuahaloho.mypropergitap.repos.local.dao.FavoriteUserDao
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser
import com.yosuahaloho.mypropergitap.repos.remote.ApiService
import kotlinx.coroutines.flow.flow
import com.yosuahaloho.mypropergitap.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
) {

    /**
     * This Function returns the default list of users that will display in home
     */
    fun getDefaultUser() = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getDefaultUsers()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return detailed user data when one of the item lists clicked
     */
    fun getDetailUser(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return user search results based on the query entered
     */
    fun getSearchUser(query: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.searchUser(query)
            emit(Result.Success(response.items))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return list of followers user based on username
     */
    fun getFollowers(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return list of following user based on username
     */
    fun getFollowing(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    suspend fun addToFavorite(user: FavoriteUser) {
        try {
            favoriteUserDao.insert(user)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    suspend fun isFavoriteUser(username: String): Boolean {
        try {
            return favoriteUserDao.isFavoriteUser(username)
        } catch (e: Exception) {
            Timber.e(e.message)
        }

        return false
    }

    suspend fun removeFromFavorite(user: FavoriteUser) {
        try {
            favoriteUserDao.delete(user)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    fun getFavoriteUser() = flow {
        emit(Result.Loading)
        try {
            val res = favoriteUserDao.getAllFavoriteUser()
            emit(Result.Success(res))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


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