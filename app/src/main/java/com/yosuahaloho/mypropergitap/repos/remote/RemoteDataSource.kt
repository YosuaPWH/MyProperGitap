package com.yosuahaloho.mypropergitap.repos.remote

import com.yosuahaloho.mypropergitap.repos.remote.network.ApiService

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getDefaultUser() = apiService.getDefaultUsers()

    suspend fun getDetailUser(username: String) = apiService.getDetailUser(username)

    suspend fun getSearchUser(username: String, page: Int) = apiService.searchUser(username, page)

    suspend fun getFollowers(username: String, page: Int) = apiService.getFollowers(username, page)

    suspend fun getFollowing(username: String, page: Int) = apiService.getFollowing(username, page)
}