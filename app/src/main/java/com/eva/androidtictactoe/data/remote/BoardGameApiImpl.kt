package com.eva.androidtictactoe.data.remote

import com.eva.androidtictactoe.BuildConfig
import com.eva.androidtictactoe.data.remote.dto.BoardGameRoomDataDto
import com.eva.androidtictactoe.data.remote.dto.ReceiveEventsDto
import com.eva.androidtictactoe.data.remote.dto.SendEventsDto
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class BoardGameApiImpl(
	private val client: HttpClient
) : BoardGameApiFacade {


	private val socketHost = "${URLProtocol.WSS.name}/${BuildConfig.BASE_URI}"

	private var _webSocketSession: DefaultClientWebSocketSession? = null

	override val serverMessage: MutableStateFlow<String>
		get() = MutableStateFlow(value = "")

	override val boardGameData: MutableStateFlow<BoardGameRoomDataDto>
		get() = MutableStateFlow(value = BoardGameRoomDataDto())

	override suspend fun onConnect(clientId: String, userName: String?) {

		val wsPath = ApiPaths.AnonymousGamePath(
			clientId = clientId, userName = userName
		)

		client.webSocket(
			method = HttpMethod.Get, path = wsPath.route, host = socketHost
		) {
			_webSocketSession = this
		}
	}

	override suspend fun onConnect(roomId: String, clientId: String, userName: String?) {

		val wsPath = ApiPaths.RoomBasedGamePath(
			roomId = roomId, clientId = clientId, userName = userName
		)

		client.webSocket(
			method = HttpMethod.Get, path = wsPath.route, host = socketHost
		) {

			_webSocketSession = this
		}
	}

	override suspend fun onDisconnect() {
		_webSocketSession?.close(
			reason = CloseReason(
				code = CloseReason.Codes.NORMAL, message = "Close successfully"
			)
		)
		_webSocketSession = null
	}

	override suspend fun onReceive() = _webSocketSession?.let { socket ->
		socket.incoming.consumeEach { frame ->
			(frame as? Frame.Text)?.let { frameText ->
				val readText = frameText.readText()
				when (val data = Json.decodeFromString<ReceiveEventsDto>(string = readText)) {

					is ReceiveEventsDto.ReceivedGameData -> boardGameData.update { data.state }

					is ReceiveEventsDto.ReceivedMessage -> serverMessage.update { data.message }
				}
			}
		}
	} ?: Unit


	override suspend fun onSend(event: SendEventsDto) =
		_webSocketSession?.sendSerialized(event) ?: Unit
}