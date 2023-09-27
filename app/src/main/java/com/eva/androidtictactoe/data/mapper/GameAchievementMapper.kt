package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.GameAchievementDto
import com.eva.androidtictactoe.domain.model.GameAchievementModel

fun GameAchievementDto.toModel() = GameAchievementModel(
	text = text,
	winnerSymbols = winnerSymbols,
	secondaryText = secondaryText,
	winnerName = winnerName,
	isDraw = isDraw
)
