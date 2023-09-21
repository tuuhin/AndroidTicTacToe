package com.eva.androidtictactoe.data.local

import kotlinx.coroutines.flow.Flow

interface UserPreferencesFacade {

	/**
	 * Returns a [Flow] of [String] representing the saved username
	 */
	val playerNameAsFlow: Flow<String>

	/**
	 * Returns the last saved name
	 */
	suspend fun getPlayerName(): String

	/**
	 * Sets the player name
	 * @param name The new name to be updated
	 */
	suspend fun setPlayerUserName(name: String)
}