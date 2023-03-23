package com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosuahaloho.mypropergitap.repos.UserRepository

class FollowViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFollowers(username: String) = userRepository.getFollowers(username).asLiveData()

    fun getFollowing(username: String) = userRepository.getFollowing(username).asLiveData()

}