package com.yosuahaloho.mypropergitap.ui.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yosuahaloho.mypropergitap.repos.UserRepository
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getDetailUser(username: String) = userRepository.getDetailUser(username).asLiveData()

    fun addToFavorite(user: FavoriteUser) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addToFavorite(user)
            isFavoriteUser(user.username)
        }
    }

    fun removeFromFavorite(user: FavoriteUser) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.removeFromFavorite(user)
        }
    }

    fun isFavoriteUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(userRepository.isFavoriteUser(username))
        }
    }
}