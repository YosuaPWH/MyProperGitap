package com.yosuahaloho.mypropergitap.repos.local

import com.yosuahaloho.mypropergitap.repos.local.dao.FavoriteUserDao
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser

class LocalDataSource(private val favoriteUserDao: FavoriteUserDao) {

    suspend fun addToFavorite(user: FavoriteUser) = favoriteUserDao.insert(user)

    suspend fun removeFromFavorite(user: FavoriteUser) = favoriteUserDao.delete(user)

    suspend fun isFavoriteUser(username: String) = favoriteUserDao.isFavoriteUser(username)

    suspend fun getFavoriteUser() = favoriteUserDao.getAllFavoriteUser()
}