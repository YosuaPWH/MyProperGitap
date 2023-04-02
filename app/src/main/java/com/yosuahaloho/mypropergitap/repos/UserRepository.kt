package com.yosuahaloho.mypropergitap.repos

import com.yosuahaloho.mypropergitap.repos.local.LocalDataSource
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser
import com.yosuahaloho.mypropergitap.repos.remote.RemoteDataSource
import com.yosuahaloho.mypropergitap.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class UserRepository private constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) {

    /**
     * This Function returns the default list of users that will display in home
     */
    fun getDefaultUser() = flow {
        emit(Result.Loading)
        try {
            val response = remote.getDefaultUser()
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
            val response = remote.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return user search results based on the query entered
     */
    fun getSearchUser(username: String, page: Int) = flow {
        emit(Result.Loading)
        try {
            val response = remote.getSearchUser(username, page)
            emit(Result.Success(response.items))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return total count of search user
     */
    fun getSearchUserCount(username: String, page: Int = 1) = flow {
        val response = remote.getSearchUser(username, page)
        emit(response.total_count)
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return list of followers user based on username
     */
    fun getFollowers(username: String, page: Int) = flow {
        emit(Result.Loading)
        try {
            val response = remote.getFollowers(username, page)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will return list of following user based on username
     */
    fun getFollowing(username: String, page: Int) = flow {
        emit(Result.Loading)
        try {
            val response = remote.getFollowing(username, page)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    /**
     * This function will add user to favorite database
     */
    suspend fun addToFavorite(user: FavoriteUser) {
        try {
            local.addToFavorite(user)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    /**
     * This function will remvove user from favorite database
     */
    suspend fun removeFromFavorite(user: FavoriteUser) {
        try {
            local.removeFromFavorite(user)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    /**
     * This function will return boolean if user exist in favorite database
     */
    suspend fun isFavoriteUser(username: String): Boolean {
        return try {
            local.isFavoriteUser(username)
        } catch (e: Exception) {
            Timber.e(e.message)
            false
        }
    }

    /**
     * This function will return all data of user that inside favorite database
     */
    fun getFavoriteUser() = flow {
        emit(Result.Loading)
        try {
            val res = local.getFavoriteUser()
            emit(Result.Success(res))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    companion object {
        private var instance: UserRepository? = null

        fun getInstance(
            remote: RemoteDataSource,
            local: LocalDataSource
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(remote, local)
            }.also {
                instance = it
            }
    }
}