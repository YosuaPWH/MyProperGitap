package com.yosuahaloho.mypropergitap.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yosuahaloho.mypropergitap.di.Injection
import com.yosuahaloho.mypropergitap.repos.UserRepository
import com.yosuahaloho.mypropergitap.ui.favorite.FavoriteViewModel
import com.yosuahaloho.mypropergitap.ui.home.HomeViewModel
import com.yosuahaloho.mypropergitap.ui.profile.ProfileViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        HomeViewModel::class.java -> HomeViewModel(userRepository)
        FavoriteViewModel::class.java -> FavoriteViewModel(userRepository)
        ProfileViewModel::class.java -> ProfileViewModel(userRepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    } as T

    companion object {

        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideUserRepository(context))
            }.also {
                instance = it
            }
    }
}