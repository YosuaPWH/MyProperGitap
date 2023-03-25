package com.yosuahaloho.mypropergitap.repos.remote

import com.yosuahaloho.mypropergitap.repos.remote.network.ApiService

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getDefaultUser() = apiService.getDefaultUsers()

    suspend fun getDetailUser(username: String) = apiService.getDetailUser(username)

    suspend fun getSearchUser(username: String) = apiService.searchUser(username)

    suspend fun getFollowers(username: String) = apiService.getFollowers(username)

    suspend fun getFollowing(username: String) = apiService.getFollowing(username)
}