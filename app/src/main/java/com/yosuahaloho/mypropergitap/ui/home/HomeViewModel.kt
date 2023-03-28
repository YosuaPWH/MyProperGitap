package com.yosuahaloho.mypropergitap.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yosuahaloho.mypropergitap.repos.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {



    fun getDefaultUser() = userRepository.getDefaultUser().asLiveData()
    fun searchUser(username: String) = userRepository.getSearchUser(username).asLiveData()

    fun getSearchUserCount(username: String) = userRepository.getSearchUserCount(username).asLiveData()

}