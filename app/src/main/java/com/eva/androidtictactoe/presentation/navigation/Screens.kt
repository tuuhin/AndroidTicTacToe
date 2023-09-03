package com.eva.androidtictactoe.presentation.navigation

sealed class Screens(val route: String) {

	object RoomRoute : Screens(route = "/room")
	object BoardingRoute : Screens(route = "/boarding")
	object CreateRoomRoute : Screens(route = "/create")

	object JoinRoomRoute : Screens(route = "/join")

	object GameScreenWithParam : Screens(
		route = buildString {
			append("/game?")
			append(ScreenParameters.ROOM_CODE_PARAMS)
			append("=")
			append("{${ScreenParameters.ROOM_CODE_PARAMS}}")
		}
	)

	data class GameScreenWithRoomId(val roomId: String) : Screens(
		route = buildString {
			append("/game?")
			append(ScreenParameters.ROOM_CODE_PARAMS)
			append("=")
			append(roomId)
		}
	)
}
