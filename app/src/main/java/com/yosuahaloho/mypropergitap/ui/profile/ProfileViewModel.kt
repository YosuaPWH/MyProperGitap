package com.yosuahaloho.mypropergitap.ui.profile

import androidx.lifecycle.*
import com.yosuahaloho.mypropergitap.repos.UserRepository
import com.yosuahaloho.mypropergitap.utils.StoredPreferences
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: StoredPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}