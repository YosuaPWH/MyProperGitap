package com.yosuahaloho.mypropergitap.repos.local

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

        fun getDatabase(context: Context): FavoriteUserDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserDatabase::class.java,
                        "db_favorite"
                    ).build()
                }
            }
            return INSTANCE as FavoriteUserDatabase
        }
    }
}