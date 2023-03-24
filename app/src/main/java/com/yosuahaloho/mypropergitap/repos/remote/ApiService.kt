package com.yosuahaloho.mypropergitap.repos.remote

import com.yosuahaloho.mypropergitap.repos.model.DetailUser
import com.yosuahaloho.mypropergitap.repos.model.ResponseSearch
import com.yosuahaloho.mypropergitap.repos.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getDefaultUsers(
        @Query("per_page") limitNumberUser: Int = 10
    ): List<User>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ): ResponseSearch

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUser

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<User>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<User>
}