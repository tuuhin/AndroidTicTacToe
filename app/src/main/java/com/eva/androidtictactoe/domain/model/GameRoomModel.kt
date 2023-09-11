package com.eva.androidtictactoe.domain.model


data class GameRoomModel(
	val room: String,
	val board: TicTacToeBoardModel = TicTacToeBoardModel(),
	val boardCount: Int,
	val isAnonymous: Boolean
) {
	companion object {
		val BLANK_ROOM = GameRoomModel(
			room = "",
			isAnonymous = false,
			board = TicTacToeBoardModel(),
			boardCount = 1
		)
	}
}
