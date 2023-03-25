package com.yosuahaloho.mypropergitap.repos.local.dao

import androidx.room.*
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FavoriteUser)

    @Delete
    suspend fun delete(user: FavoriteUser)

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE username = :username)")
    suspend fun isFavoriteUser(username: String): Boolean

    @Query("SELECT * FROM favorite_user ORDER by username ASC")
    suspend fun getAllFavoriteUser(): List<FavoriteUser>

}