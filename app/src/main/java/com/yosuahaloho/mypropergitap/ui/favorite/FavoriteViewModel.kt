package com.yosuahaloho.mypropergitap.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yosuahaloho.mypropergitap.repos.UserRepository

class FavoriteViewModel(userRepository: UserRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _text = MutableLiveData<String>().apply {
        value = "This is Favorite Fragment"
    }
    val text: LiveData<String> = _text
}