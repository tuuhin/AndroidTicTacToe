package com.eva.androidtictactoe.utils

sealed class ApiPaths(val route: String) {

	object CreateRoomPath : ApiPaths(route = "/room/create")
	object JoinRoomPath : ApiPaths(route = "/room/join")

	data class AnonymousGamePath(
		val clientId: String,
		val userName: String? = null
	) :
		ApiPaths(
			route = buildString {
				append("/ws/game?client_id=${clientId}")
				userName?.let { name ->
					append("&username=$name")
				}
			}
		)

	data class RoomBasedGamePath(
		val clientId: String,
		val userName: String? = null,
		val roomId: String
	) : ApiPaths(
		route = buildString {

			append("/ws/game/${roomId}?client_id=${clientId}")
			userName?.let { name ->
				append("&username=$name")
			}
		}
	)
}