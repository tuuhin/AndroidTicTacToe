package com.eva.androidtictactoe.domain.repository

import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.BoardPosition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {

	val clientId: String

	val serverMessage: StateFlow<String>

	val connectionEvents: MutableSharedFlow<String>

	val gameBoard: Flow<BoardGameModel>

	suspend fun onDisConnect(): Boolean

	suspend fun connectWithRoomId(room: String, userName: String? = null)

	suspend fun connectAnonymously(userName: String? = null)

	suspend fun sendBoardData(position: BoardPosition)

}