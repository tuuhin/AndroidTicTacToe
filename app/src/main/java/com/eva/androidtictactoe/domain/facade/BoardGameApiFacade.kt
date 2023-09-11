package com.eva.androidtictactoe.domain.facade

import com.eva.androidtictactoe.data.remote.dto.BoardGameRoomDataDto
import com.eva.androidtictactoe.data.remote.dto.SendEventsDto
import kotlinx.coroutines.flow.MutableStateFlow

interface BoardGameApiFacade {

	val serverMessage: MutableStateFlow<String>

	val boardGameData: MutableStateFlow<BoardGameRoomDataDto>

	suspend fun onConnect(clientId: String, userName: String? = null)

	suspend fun onConnect(roomId: String, clientId: String, userName: String? = null)

	suspend fun onDisconnect()

	suspend fun onReceive()

	suspend fun onSend(event: SendEventsDto)
}