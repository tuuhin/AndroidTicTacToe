package com.eva.androidtictactoe.domain.model


data class GameRoomModel(
	val room: String,
	val board: TicTacToeBoardModel = TicTacToeBoardModel(),
	val boardCount: Int,
	val currentBoardNumber: Int,
) {
	companion object {
		val BLANK_ROOM = GameRoomModel(
			room = "",
			board = TicTacToeBoardModel(),
			boardCount = 1,
			currentBoardNumber = 0,
		)
	}
}
