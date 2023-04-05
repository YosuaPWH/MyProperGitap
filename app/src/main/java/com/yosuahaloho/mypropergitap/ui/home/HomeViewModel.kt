package com.yosuahaloho.mypropergitap.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yosuahaloho.mypropergitap.repos.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDefaultUser() = userRepository.getDefaultUser()
    fun searchUser(username: String, page: Int) = userRepository.getSearchUser(username, page)

    fun getSearchUserCountAsync(username: String) = viewModelScope.async {
        userRepository.getSearchUserCount(username).first()
    }

}