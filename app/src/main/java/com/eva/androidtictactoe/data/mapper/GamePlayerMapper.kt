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
	symbol = GameSymbols.fromSymbol(playerSymbol)
)

fun GamePlayerModel.toDto() = GamePlayerDto(
	userName = userName,
	clientId = clientId,
	winCount = winCount,
	drawCount = drawCount,
	playerSymbol = symbol.symbol,
	lostCount = looseCount
)