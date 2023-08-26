package com.eva.androidtictactoe.presentation.navigation

sealed class Screens(val route: String) {
    object BoardingScreen : Screens(route = "/boarding")

    object GameScreen : Screens(route = "/game/{${ScreenParameters.ROOM_CODE_PARAMS}}")
}
