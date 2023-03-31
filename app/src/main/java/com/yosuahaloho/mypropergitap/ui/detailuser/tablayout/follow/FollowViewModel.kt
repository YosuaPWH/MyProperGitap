package com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosuahaloho.mypropergitap.repos.UserRepository

class FollowViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFollowers(username: String, page: Int) = userRepository.getFollowers(username, page).asLiveData()

    fun getFollowing(username: String, page: Int) = userRepository.getFollowing(username, page).asLiveData()

}