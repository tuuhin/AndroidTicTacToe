package com.eva.androidtictactoe.domain.model

data class BoardGameModel(
	val game: GameRoomModel,
	val player: GamePlayerModel?,
	val opponent: GamePlayerModel?,
	val isReady: Boolean
)