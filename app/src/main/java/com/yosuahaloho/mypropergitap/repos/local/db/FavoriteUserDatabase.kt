package com.yosuahaloho.mypropergitap.repos.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yosuahaloho.mypropergitap.repos.local.dao.FavoriteUserDao
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {

        private var INSTANCE: FavoriteUserDatabase? = null

        fun getDatabase(context: Context): FavoriteUserDatabase =
            INSTANCE ?: synchronized(this) {  // Blok synchronized digunakan untuk memastikan bahwa
                INSTANCE ?: Room.databaseBuilder( // hanya satu thread yang dapat mengakses blok kode
                    context.applicationContext, // tersebut pada satu waktu.
                    FavoriteUserDatabase::class.java,
                    "db_favorite"
                ).build()
            }

    }
}