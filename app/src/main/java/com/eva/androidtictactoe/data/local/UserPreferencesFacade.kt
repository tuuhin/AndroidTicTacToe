package com.eva.androidtictactoe.data.local

import kotlinx.coroutines.flow.Flow

interface UserPreferencesFacade {

    val playerUserName: Flow<String>
    suspend fun setPlayerUserName(name: String)
}