package com.eva.androidtictactoe.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.eva.androidtictactoe.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.datastore by preferencesDataStore(name = AppConstants.DATASTORE_NAME)

class UserPreferences(
	private val context: Context
) : UserPreferencesFacade {

	private val userNamePrefKey = stringPreferencesKey(name = AppConstants.USER_NAME_KEY)

	override val playerNameAsFlow: Flow<String>
		get() = context.datastore.data.map { pref -> pref[userNamePrefKey] ?: "" }
			.flowOn(Dispatchers.IO)

	override suspend fun getPlayerName() = withContext(Dispatchers.IO) { playerNameAsFlow.first() }

	override suspend fun setPlayerUserName(name: String) {
		context.datastore
			.edit { pref -> pref[userNamePrefKey] = name }
	}
}