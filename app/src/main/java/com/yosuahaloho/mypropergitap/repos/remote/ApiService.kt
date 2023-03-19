package com.yosuahaloho.mypropergitap.repos.remote

import com.yosuahaloho.mypropergitap.repos.model.DetailUser
import com.yosuahaloho.mypropergitap.repos.model.ResponseSearch
import com.yosuahaloho.mypropergitap.repos.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getDefaultUsers(
        @Query("per_page") limitNumberUser: Int = 10
    ): List<User>

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String,
        @Query("per_page") limitNumberUser: Int = 10
    ): Call<ResponseSearch>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") limitNumberUser: Int
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") limitNumberUser: Int
    )
}