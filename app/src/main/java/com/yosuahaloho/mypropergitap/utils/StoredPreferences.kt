package com.yosuahaloho.mypropergitap.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoredPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val themeKey = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoredPreferences? = null

        private val Context.dataStores: DataStore<Preferences> by preferencesDataStore(name = "settings")

        fun getInstance(context: Context): StoredPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = StoredPreferences(context.applicationContext.dataStores)
                INSTANCE = instance
                instance
            }
        }
    }

}