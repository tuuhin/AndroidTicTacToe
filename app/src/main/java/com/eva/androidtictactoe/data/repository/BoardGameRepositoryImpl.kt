package com.eva.androidtictactoe.data.repository

import com.eva.androidtictactoe.data.mapper.toDto
import com.eva.androidtictactoe.data.mapper.toModel
import com.eva.androidtictactoe.data.remote.dto.SendEventsDto
import com.eva.androidtictactoe.data.remote.dto.SendGameDataDto
import com.eva.androidtictactoe.domain.facade.BoardGameApiFacade
import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.BoardPosition
import com.eva.androidtictactoe.domain.repository.GameRepository
import com.eva.androidtictactoe.utils.Resource
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.util.generateNonce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardGameRepositoryImpl(
	private val boardGame: BoardGameApiFacade,
) : GameRepository {
	override val clientId: String
		get() = generateNonce()
	override val gameBoard: Flow<BoardGameModel>
		get() = boardGame.boardGameData.map { it.toModel(clientId) }

	override val serverMessage: StateFlow<String>
		get() = boardGame.serverMessage

	override val connectionEvents: MutableSharedFlow<String>
		get() = MutableSharedFlow()

	override suspend fun connectWithRoomId(room: String, userName: String?) {
		return withContext(Dispatchers.IO) {
			try {
				boardGame.onConnect(roomId = room, userName = userName, clientId = clientId)
				val received = launch { boardGame.onReceive() }
				received.join()
			} catch (e: WebSocketException) {
				connectionEvents.emit(value = e.localizedMessage ?: "Websocket Exception")
			} catch (e: Exception) {
				connectionEvents.emit(value = e.localizedMessage ?: "exception occurred")
			}
		}
	}

	override suspend fun connectAnonymously(userName: String?) {
		return withContext(Dispatchers.IO) {
			try {
				boardGame.onConnect(clientId = clientId, userName = userName)
				val received = launch { boardGame.onReceive() }
				received.join()
			} catch (e: WebSocketException) {
				connectionEvents.emit(value = e.localizedMessage ?: "Websocket Exception")
			} catch (e: Exception) {
				connectionEvents.emit(value = e.localizedMessage ?: "exception occurred")
			}
		}
	}

	override suspend fun onDisConnect(): Resource<Boolean> {
		return try {
			boardGame.onDisconnect()
			Resource.Success(data = true)
		} catch (e: WebSocketException) {
			e.printStackTrace()
			Resource.Error(message = e.message ?: "Websocket Error")
		} catch (e: Exception) {
			e.printStackTrace()
			Resource.Error(message = e.message ?: "Error Occurred on disconnecting the session")
		}
	}

	override suspend fun sendBoardData(position: BoardPosition) {
		val sendDto = SendEventsDto.SendGameData(
			data = SendGameDataDto(
				positionDto = position.toDto(),
				clientId = clientId
			)
		)
		try {
			boardGame.onSend(sendDto)
		} catch (e: WebSocketException) {
			connectionEvents.emit(value = e.localizedMessage ?: "Websocket Exception occured")
		} catch (e: Exception) {
			connectionEvents.emit(value = e.localizedMessage ?: "Exception Occured")
		}
	}
}
