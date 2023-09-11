package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.GamePlayerDto
import com.eva.androidtictactoe.domain.model.GamePlayerModel
import com.eva.androidtictactoe.domain.model.GameSymbols

fun GamePlayerDto.toModel() = GamePlayerModel(
	userName = userName,
	clientId = clientId,
	winCount = winCount,
	drawCount = drawCount,
	looseCount = lostCount,
	playerSymbol = GameSymbols.fromSymbol(playerSymbol)
)

fun GamePlayerModel.toDto() = GamePlayerDto(
	userName = userName,
	clientId = clientId,
	winCount = winCount,
	drawCount = drawCount,
	playerSymbol = playerSymbol.symbol,
	lostCount = looseCount
)