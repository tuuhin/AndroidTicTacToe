package com.eva.androidtictactoe.domain.model

data class GameAchievementModel(
	val text: String,
	val winnerSymbols: GameSymbols? = null,
	val secondaryText: String? = null,
	val winnerName: String? = null,
	val isDraw: Boolean = false
)
