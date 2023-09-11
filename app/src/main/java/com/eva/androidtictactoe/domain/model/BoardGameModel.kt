package com.eva.androidtictactoe.domain.model

data class BoardGameModel(
	val boardGameModel: GameRoomModel,
	val player: GamePlayerModel?,
	val opponent: GamePlayerModel?,
	val isReady: Boolean
) {
	companion object {
		val UN_INITIALIZE_GAME = BoardGameModel(
			boardGameModel = GameRoomModel.BLANK_ROOM,
			isReady = false,
			player = null,
			opponent = null
		)
	}
}