package com.eva.androidtictactoe.data.mapper

import com.eva.androidtictactoe.data.remote.dto.GameRoomDto
import com.eva.androidtictactoe.domain.model.GameRoomModel
import com.eva.androidtictactoe.domain.model.GameSymbols
import com.eva.androidtictactoe.domain.model.TicTacToeBoardModel

fun GameRoomDto.toModel() = GameRoomModel(
	room = room,
	board = TicTacToeBoardModel(
		boardFace = boardLayout.map { row ->
			row.map { GameSymbols.fromSymbol(it) }
		}, isDraw = isDraw
	),
	boardCount = boardCount,
	currentBoardNumber = currentBoard
)

fun GameRoomModel.toDto() = GameRoomDto(
	room = room,
	boardLayout = board.boardFace.map { row -> row.map { it.symbol } },
	isDraw = board.isDraw,
	boardCount = boardCount,
	currentBoard = currentBoardNumber
)