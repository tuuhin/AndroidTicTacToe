package com.eva.androidtictactoe.domain.model

data class TicTacToeBoardModel(
	val boardFace: TicTacToeBoardFace = emptyBoardState(),
	val isDraw: Boolean = false
) {
	companion object {
		fun emptyBoardState(): TicTacToeBoardFace =
			List(3) { List(3) { GameSymbols.Blank } }
	}
}
