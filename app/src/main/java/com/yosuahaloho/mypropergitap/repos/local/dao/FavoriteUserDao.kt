package com.yosuahaloho.mypropergitap.repos.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)

    @Delete
    fun delete(user: FavoriteUser)

    @Query("SELECT * FROM favorite_user ORDER by username ASC")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>
}