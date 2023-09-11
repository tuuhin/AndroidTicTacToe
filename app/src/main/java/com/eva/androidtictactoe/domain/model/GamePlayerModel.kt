package com.eva.androidtictactoe.domain.model

data class GamePlayerModel(
	val userName: String,
	val clientId: String,
	val winCount: Int = 0,
	val drawCount: Int = 0,
	val looseCount: Int = 0,
	val playerSymbol: GameSymbols
)
