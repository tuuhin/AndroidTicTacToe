package com.eva.androidtictactoe.domain.model

data class GameAchievementModel(
	val text: String,
	val winnerSymbols: GameSymbols,
	val secondaryText: String? = null,
	val winnerName: String? = null
)
