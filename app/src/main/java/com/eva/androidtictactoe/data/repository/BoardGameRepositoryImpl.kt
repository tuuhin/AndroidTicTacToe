package com.eva.androidtictactoe.data.repository

import com.eva.androidtictactoe.data.mapper.toDto
import com.eva.androidtictactoe.data.mapper.toModel
import com.eva.androidtictactoe.data.remote.dto.SendGameDataDto
import com.eva.androidtictactoe.domain.facade.BoardGameApiFacade
import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.BoardPosition
import com.eva.androidtictactoe.domain.model.GameAchievementModel
import com.eva.androidtictactoe.domain.repository.GameRepository
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.util.generateNonce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardGameRepositoryImpl(
	private val gameFacade: BoardGameApiFacade,
) : GameRepository {

	private var _clientId: String? = null

	override val clientId: String
		get() = _clientId ?: kotlin.run {
			val uniqueId = generateNonce()
			_clientId = uniqueId
			uniqueId
		}

	override val gameBoard: Flow<BoardGameModel>
		get() = gameFacade.boardGameData.map { it.toModel(clientId) }

	override val serverMessage: StateFlow<String>
		get() = gameFacade.serverMessage

	override val gameAchievements: Flow<GameAchievementModel>
		get() = gameFacade.gameAchievementData.map { it.toModel() }


	private val _connectionEvents = MutableSharedFlow<String>()

	override val connectionEvents: SharedFlow<String>
		get() = _connectionEvents

	override suspend fun connectWithRoomId(room: String, userName: String?) {
		return withContext(Dispatchers.IO) {
			try {
				gameFacade.onConnect(
					roomId = room,
					userName = userName,
					clientId = clientId,
					socketBlock = {
						val receive = launch(Dispatchers.IO) { gameFacade.onReceive() }
						receive.join()
					},
				)

			} catch (e: WebSocketException) {
				_connectionEvents.emit(value = e.localizedMessage ?: "Websocket Exception")
			} catch (e: Exception) {
				_connectionEvents.emit(value = e.localizedMessage ?: "exception occurred")
			}
		}
	}


	override suspend fun connectAnonymously(userName: String?) {
		return withContext(Dispatchers.IO) {
			try {
				gameFacade.onConnect(
					clientId = clientId,
					userName = userName,
					socketBlock = {
						val receive = launch(Dispatchers.IO) { gameFacade.onReceive() }
						receive.join()
					},
				)
			} catch (e: WebSocketException) {

				_connectionEvents.emit(value = e.localizedMessage ?: "Websocket Exception")
			} catch (e: Exception) {
				_connectionEvents.emit(value = e.localizedMessage ?: "exception occurred")
			}
		}
	}


	override suspend fun onDisConnect(): Boolean = try {
		gameFacade.onDisconnect()
		true
	} catch (e: WebSocketException) {
		e.printStackTrace()
		false
	} catch (e: Exception) {
		e.printStackTrace()
		false
	}

	override suspend fun sendBoardData(position: BoardPosition) {
		return withContext(Dispatchers.IO) {
			val sendDto = SendGameDataDto(
				positionDto = position.toDto(),
				clientId = clientId
			)
			try {
				gameFacade.onSend(sendDto)
			} catch (e: WebSocketException) {
				_connectionEvents.emit(value = e.localizedMessage ?: "Websocket Exception occurred")
			} catch (e: Exception) {
				_connectionEvents.emit(value = e.localizedMessage ?: "Exception Occurred")
			}
		}
	}
}
