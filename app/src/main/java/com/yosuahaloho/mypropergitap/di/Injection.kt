package com.yosuahaloho.mypropergitap.di

import android.content.Context
import com.yosuahaloho.mypropergitap.repos.UserRepository
import com.yosuahaloho.mypropergitap.repos.local.FavoriteUserDatabase
import com.yosuahaloho.mypropergitap.repos.remote.ApiConfig

object Injection {

    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getInstance
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        return UserRepository.getInstance(apiService, dao)
    }
}