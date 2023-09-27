package com.eva.androidtictactoe.data.remote.dto

import com.eva.androidtictactoe.domain.model.GameSymbols
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameAchievementDto(

	@SerialName("text")
	val text: String = "",

	@SerialName("secondary")
	val secondaryText: String? = null,

	@SerialName("winner_symbol")
	val winnerSymbols: GameSymbols? = null,

	@SerialName("winner_name")
	val winnerName: String? = null,

	@SerialName("is_draw")
	val isDraw: Boolean = false,
)
