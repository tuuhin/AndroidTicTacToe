package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.BoardGameRoomDataDto
import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.GameSymbols

fun BoardGameRoomDataDto.toModel(clientId: String): BoardGameModel {
	val isOPlayer = playerO?.clientId == clientId

	return BoardGameModel(
		player = if (isOPlayer) playerO?.toModel() else playerX?.toModel(),
		opponent = if (isOPlayer) playerX?.toModel() else playerO?.toModel(),
		isReady = board.isReady,
		game = board.toModel(),
		winningSymbols = board.winningSymbols?.let { GameSymbols.fromSymbol(it) }
	)
}