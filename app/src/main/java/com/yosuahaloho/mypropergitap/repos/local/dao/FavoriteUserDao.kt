package com.yosuahaloho.mypropergitap.repos.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yosuahaloho.mypropergitap.repos.model.User

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM favorite_user ORDER by username ASC")
    fun getAllFavoriteUser(): LiveData<List<User>>
}