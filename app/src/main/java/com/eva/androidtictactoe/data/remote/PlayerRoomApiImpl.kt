package com.eva.androidtictactoe.data.remote

import com.eva.androidtictactoe.BuildConfig
import com.eva.androidtictactoe.data.remote.dto.CreateRoomSerializer
import com.eva.androidtictactoe.data.remote.dto.RoomSerializer
import com.eva.androidtictactoe.data.remote.dto.VerifiedRoomSerializer
import com.eva.androidtictactoe.domain.facade.PlayerRoomApiFacade
import com.eva.androidtictactoe.utils.ApiPaths
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path

class PlayerRoomApiImpl(
	private val client: HttpClient
) : PlayerRoomApiFacade {
	override suspend fun createRoom(rounds: Int): RoomSerializer {
		val response = client.post {
			url {
				protocol = URLProtocol.HTTPS
				host = BuildConfig.BASE_URI
				path(ApiPaths.CreateRoomPath.route)
			}
			setBody(CreateRoomSerializer(rounds = rounds))
		}
		return when {
			response.status.isSuccess() -> response.body()
			response.status.value in 400 until 500 -> throw ClientRequestException(
				response = response, cachedResponseText = response.bodyAsText()
			)

			else -> throw ServerResponseException(
				response, cachedResponseText = response.bodyAsText()
			)
		}
	}

	override suspend fun checkRoom(roomId: String): VerifiedRoomSerializer {
		val response = client.post {
			url {
				protocol = URLProtocol.HTTPS
				host = BuildConfig.BASE_URI
				path(ApiPaths.CreateRoomPath.route)
			}
			setBody(RoomSerializer(room = roomId))
		}
		return when {
			response.status.isSuccess() -> response.body()
			response.status.value in 400 until 500 -> throw ClientRequestException(
				response = response, cachedResponseText = response.bodyAsText()
			)

			else -> throw ServerResponseException(
				response, cachedResponseText = response.bodyAsText()
			)
		}
	}
}