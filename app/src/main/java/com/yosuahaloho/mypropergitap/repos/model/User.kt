package com.yosuahaloho.mypropergitap.repos.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val username: String,
    val id: Int,
    val avatar_url: String
)
