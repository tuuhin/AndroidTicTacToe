package com.eva.androidtictactoe.domain.model

data class BoardGameModel(
	val game: GameRoomModel,
	val player: GamePlayerModel?,
	val opponent: GamePlayerModel?,
	val isReady: Boolean,
	val winningSymbols: GameSymbols? = null
) {
	companion object {
		val UNINITIALIZED_GAME_ROOM = BoardGameModel(
			game = GameRoomModel.BLANK_ROOM,
			isReady = false,
			player = null,
			opponent = null
		)
	}
}