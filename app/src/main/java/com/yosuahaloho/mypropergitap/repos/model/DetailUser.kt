package com.yosuahaloho.mypropergitap.repos.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    @SerializedName("login")
    val username: String,
    val name: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val avatar_url: String
) : Parcelable
