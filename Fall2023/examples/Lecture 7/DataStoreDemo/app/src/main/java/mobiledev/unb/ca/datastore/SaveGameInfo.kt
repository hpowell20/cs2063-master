package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SaveGameInfo(private val context: Context) {

    // Create the dataStore and give it a name same as user_pref
    // Create some keys we will use them to store and retrieve the data
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("game_prefs")
        val HIGH_SCORE_KEY = intPreferencesKey("HIGH_SCORE")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys
    suspend fun storeUserInfo(score: Int) {
        context.dataStore.edit { preferences ->
            preferences[HIGH_SCORE_KEY] = score
        }
    }

    // get the score age
    val scoreFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[HIGH_SCORE_KEY] ?: 0
    }
}
