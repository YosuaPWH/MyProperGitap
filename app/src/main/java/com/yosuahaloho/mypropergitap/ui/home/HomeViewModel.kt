package com.yosuahaloho.mypropergitap.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosuahaloho.mypropergitap.repos.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDefaultUser() = userRepository.getDefaultUser().asLiveData()
    fun searchUser(username: String) = userRepository.getSearchUser(username).asLiveData()
}