package com.eva.androidtictactoe.domain.facade

import com.eva.androidtictactoe.data.remote.dto.BoardGameRoomDataDto
import com.eva.androidtictactoe.data.remote.dto.GameAchievementDto
import com.eva.androidtictactoe.data.remote.dto.SendGameDataDto
import io.ktor.websocket.CloseReason
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BoardGameApiFacade {

	val serverMessage: StateFlow<String>

	val boardGameData: StateFlow<BoardGameRoomDataDto>

	val gameAchievementData: SharedFlow<GameAchievementDto>

	suspend fun onConnect(
		clientId: String,
		userName: String? = null,
		socketBlock: suspend () -> Unit
	)

	suspend fun onConnect(
		roomId: String,
		clientId: String,
		userName: String? = null,
		socketBlock: suspend () -> Unit
	)

	suspend fun onDisconnect(
		reason: CloseReason? = null,
		disconnectBlock: (suspend () -> Unit)? = null
	)

	suspend fun onReceive()

	suspend fun onSend(event: SendGameDataDto)
}