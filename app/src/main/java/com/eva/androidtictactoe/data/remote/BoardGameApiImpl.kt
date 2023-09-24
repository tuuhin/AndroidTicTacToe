package com.eva.androidtictactoe.data.remote

import com.eva.androidtictactoe.BuildConfig
import com.eva.androidtictactoe.data.remote.dto.BoardGameRoomDataDto
import com.eva.androidtictactoe.data.remote.dto.GameAchievementDto
import com.eva.androidtictactoe.data.remote.dto.ReceiveEventsDto
import com.eva.androidtictactoe.data.remote.dto.SendGameDataDto
import com.eva.androidtictactoe.domain.facade.BoardGameApiFacade
import com.eva.androidtictactoe.utils.ApiPaths
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class BoardGameApiImpl(
	private val client: HttpClient
) : BoardGameApiFacade {

	private var _webSocketSession: DefaultClientWebSocketSession? = null

	private val _serverMessages = MutableStateFlow(value = "")
	override val serverMessage: MutableStateFlow<String>
		get() = _serverMessages

	private val _boardGame = MutableStateFlow(value = BoardGameRoomDataDto())
	override val boardGameData: MutableStateFlow<BoardGameRoomDataDto>
		get() = _boardGame


	private val _achievements = MutableSharedFlow<GameAchievementDto>()
	override val gameAchievementData: MutableSharedFlow<GameAchievementDto>
		get() = _achievements


	override suspend fun onConnect(
		clientId: String,
		userName: String?,
		socketBlock: suspend () -> Unit
	) {

		val wsPath = ApiPaths.AnonymousGamePath(
			clientId = clientId, userName = userName
		)

		client.webSocket(
			method = HttpMethod.Get,
			host = BuildConfig.BASE_URI,
			path = wsPath.route,
			request = {
				url {
					protocol =
						if (BuildConfig.IS_CONNECTION_SECURE)
							URLProtocol.WSS
						else URLProtocol.WS
				}
			}
		) {
			_webSocketSession = this
			socketBlock()
		}
	}

	override suspend fun onConnect(
		roomId: String,
		clientId: String,
		userName: String?,
		socketBlock: suspend () -> Unit
	) {

		val wsPath = ApiPaths.RoomBasedGamePath(
			roomId = roomId, clientId = clientId, userName = userName
		)

		client.webSocket(
			method = HttpMethod.Get,
			host = BuildConfig.BASE_URI,
			path = wsPath.route,
			request = {
				url {
					protocol =
						if (BuildConfig.IS_CONNECTION_SECURE)
							URLProtocol.WSS
						else URLProtocol.WS
				}
			}
		) {
			_webSocketSession = this
			socketBlock()
		}
	}

	override suspend fun onDisconnect(
		reason: CloseReason?,
		disconnectBlock: (suspend () -> Unit)?
	) {
		disconnectBlock?.let { it() }
		_webSocketSession?.close(
			reason = reason ?: CloseReason(
				code = CloseReason.Codes.NORMAL,
				message = "Close successfully"
			)
		)
		_webSocketSession = null
	}

	override suspend fun onReceive() = _webSocketSession?.let { socket ->
		socket.incoming.consumeEach { frame ->
			(frame as? Frame.Text)?.let { frameText ->
				val readText = frameText.readText()
				when (val data = Json.decodeFromString<ReceiveEventsDto>(string = readText)) {

					is ReceiveEventsDto.ReceivedGameData -> _boardGame.update { data.state }

					is ReceiveEventsDto.ReceivedMessage -> _serverMessages.update { data.message }

					is ReceiveEventsDto.GameAchievementState -> _achievements.emit(data.result)

				}
			}
		}
	} ?: Unit


	override suspend fun onSend(event: SendGameDataDto) =
		_webSocketSession?.sendSerialized(event) ?: Unit
}