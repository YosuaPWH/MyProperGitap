package com.yosuahaloho.mypropergitap.repos.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "avatar_url")
    val avatar_url: String
)
