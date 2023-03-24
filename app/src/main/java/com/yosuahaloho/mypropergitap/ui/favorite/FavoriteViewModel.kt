package com.yosuahaloho.mypropergitap.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosuahaloho.mypropergitap.repos.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavoriteUser() = userRepository.getFavoriteUser().asLiveData()

}