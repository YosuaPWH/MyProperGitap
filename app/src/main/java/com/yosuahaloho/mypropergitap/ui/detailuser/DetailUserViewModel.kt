package com.yosuahaloho.mypropergitap.ui.detailuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosuahaloho.mypropergitap.repos.UserRepository

class DetailUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDetailUser(username: String) = userRepository.getDetailUser(username).asLiveData()
}