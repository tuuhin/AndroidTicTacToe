package com.eva.androidtictactoe.presentation.utils

import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.GameAchievementModel
import com.eva.androidtictactoe.domain.model.GamePlayerModel
import com.eva.androidtictactoe.domain.model.GameRoomModel
import com.eva.androidtictactoe.domain.model.GameSymbols
import com.eva.androidtictactoe.domain.model.RoomResponseModel
import kotlin.random.Random

object FakePreview {

	val DIAGONAL_BOARD_FILLED_BY_X =
		List(3) { x -> List(3) { y -> if (x == y) GameSymbols.XSymbol else GameSymbols.Blank } }
	val DIAGONAL_FILLED_BOARD_BY_O =
		List(3) { x -> List(3) { y -> if (x == y) GameSymbols.OSymbol else GameSymbols.Blank } }

	val BOARD_RANDOM_FILLED_BY_X_AND_O = List(3) { x ->
		List(3) {
			if (Random(0).nextInt(3) == x)
				GameSymbols.OSymbol
			else GameSymbols.XSymbol
		}
	}

	val FAKE_GAME_PLAYER_MODEL = GamePlayerModel(
		userName = "Anonymous",
		clientId = "SomId",
		symbol = GameSymbols.OSymbol
	)

	val FAKE_ROOM_RESPONSE_MODEL = RoomResponseModel(
		roomId = "1234567890",
		rounds = 10
	)
	val FAKE_UN_INITIALIZE_GAME = BoardGameModel(
		game = GameRoomModel.BLANK_ROOM,
		isReady = false,
		player = null,
		opponent = null
	)

	val FAKE_GOING_GAME = BoardGameModel(
		game = GameRoomModel(
			room = "Something",
			boardCount = 3,
			currentBoardNumber = 1
		),
		isReady = true,
		player = FAKE_GAME_PLAYER_MODEL.copy(userName = "One", symbol = GameSymbols.XSymbol),
		opponent = FAKE_GAME_PLAYER_MODEL
	)

	val FAKE_ACHIEVEMENT_MODEL = GameAchievementModel(
		text = "The boards are over",
		winnerName = "Anonymous",
		winnerSymbols = GameSymbols.XSymbol,
		secondaryText = "The user anonymous have won the game by 2 points"
	)
}
