package com.eva.androidtictactoe.domain.repository

import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.BoardPosition
import com.eva.androidtictactoe.domain.model.GameAchievementModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {

	val clientId: String

	val serverMessage: StateFlow<String>

	val connectionEvents: SharedFlow<String>

	val gameBoard: Flow<BoardGameModel>

	val gameAchievements: Flow<GameAchievementModel>

	suspend fun onDisConnect(): Boolean

	suspend fun connectWithRoomId(room: String, userName: String? = null)

	suspend fun connectAnonymously(userName: String? = null)

	suspend fun sendBoardData(position: BoardPosition)

}