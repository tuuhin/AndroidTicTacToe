package com.eva.androidtictactoe.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val context: Context
) : UserPreferencesFacade {
    private val Context.datastore by preferencesDataStore("USER_PREFERENCES")

    private val userNamePrefKey = stringPreferencesKey("USER_NAME")
    override val playerUserName: Flow<String>
        get() = context.datastore.data.map { pref -> pref[userNamePrefKey] ?: "" }

    override suspend fun setPlayerUserName(name: String) {
        context.datastore
            .edit { pref -> pref[userNamePrefKey] = name }
    }
}