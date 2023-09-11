package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.BoardGameRoomDataDto
import com.eva.androidtictactoe.domain.model.BoardGameModel

fun BoardGameRoomDataDto.toModel(clientId: String): BoardGameModel {
	val isOPlayer = playerO?.clientId == clientId

	return BoardGameModel(
		player = if (isOPlayer) playerO?.toModel() else playerX?.toModel(),
		opponent = if (!isOPlayer) playerO?.toModel() else playerX?.toModel(),
		isReady = didAllJoined,
		boardGameModel = board.toModel()
	)
}