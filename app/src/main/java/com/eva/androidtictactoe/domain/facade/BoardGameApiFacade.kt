package com.eva.androidtictactoe.domain.facade

import com.eva.androidtictactoe.data.remote.dto.BoardGameRoomDataDto
import com.eva.androidtictactoe.data.remote.dto.SendEventsDto
import io.ktor.websocket.CloseReason
import kotlinx.coroutines.flow.MutableStateFlow

interface BoardGameApiFacade {

	val serverMessage: MutableStateFlow<String>

	val boardGameData: MutableStateFlow<BoardGameRoomDataDto>

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

	suspend fun onSend(event: SendEventsDto)
}