package com.eva.androidtictactoe.data.remote

import com.eva.androidtictactoe.data.remote.dto.CreateRoomSerializer
import com.eva.androidtictactoe.data.remote.dto.RoomSerializer
import com.eva.androidtictactoe.data.remote.dto.VerifiedRoomSerializer
import com.eva.androidtictactoe.domain.repository.PlayerRoomApiFacade
import com.eva.androidtictactoe.utils.ApiPaths
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.http.path

class PlayerRoomApiImpl(
	private val client: HttpClient
) : PlayerRoomApiFacade {
	override suspend fun createRoom(rounds: Int): RoomSerializer {
		val response = client.post {
			url {
				path(ApiPaths.ROOM_ROUTE + ApiPaths.CREATE_ROOM_PATH)
			}
			setBody(CreateRoomSerializer(rounds = rounds))
		}
		return when {
			response.status.isSuccess() -> response.body()
			else -> throw ClientRequestException(
				response,
				cachedResponseText = response.bodyAsText()
			)
		}
	}

	override suspend fun checkRoom(roomId: String): VerifiedRoomSerializer {
		val response = client.post {
			url {
				path(ApiPaths.ROOM_ROUTE + ApiPaths.CHECK_ROOM_PATH)
			}
			setBody(RoomSerializer(room = roomId))
		}
		return when {
			response.status.isSuccess() -> response.body()
			else -> throw ClientRequestException(
				response,
				cachedResponseText = response.bodyAsText()
			)
		}
	}
}