package com.yosuahaloho.mypropergitap.di

import android.content.Context
import com.yosuahaloho.mypropergitap.repos.UserRepository
import com.yosuahaloho.mypropergitap.repos.local.db.FavoriteUserDatabase
import com.yosuahaloho.mypropergitap.repos.local.LocalDataSource
import com.yosuahaloho.mypropergitap.repos.remote.RemoteDataSource
import com.yosuahaloho.mypropergitap.repos.remote.network.ApiConfig

object Injector {

    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getInstance
        val remote = RemoteDataSource(apiService)

        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val local = LocalDataSource(dao)
        return UserRepository.getInstance(remote, local)
    }

}