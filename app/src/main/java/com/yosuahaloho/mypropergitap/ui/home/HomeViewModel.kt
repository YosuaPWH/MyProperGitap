package com.yosuahaloho.mypropergitap.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosuahaloho.mypropergitap.repos.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDefaultUser() = userRepository.getDefaultUser().asLiveData()
    fun searchUser(query: String) = userRepository.getSearchUser(query).asLiveData()
}