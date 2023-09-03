package com.eva.androidtictactoe.presentation.utils

import com.eva.androidtictactoe.domain.model.BoardSymbols
import com.eva.androidtictactoe.domain.model.GamePlayerModel
import com.eva.androidtictactoe.domain.model.RoomResponseModel
import kotlin.random.Random

object FakePreview {

	val DIAGONAL_BOARD_FILLED_BY_X =
		List(3) { x -> List(3) { y -> if (x == y) BoardSymbols.XSymbol else BoardSymbols.Blank } }
	val DIAGONAL_FILLED_BOARD_BY_O =
		List(3) { x -> List(3) { y -> if (x == y) BoardSymbols.OSymbol else BoardSymbols.Blank } }

	val BOARD_RANDOM_FILLED_BY_X_AND_O = List(3) { x ->
		List(3) {
			if (Random(0).nextInt(3) == x)
				BoardSymbols.OSymbol
			else BoardSymbols.XSymbol
		}
	}

	val FAKE_GAME_PLAYER_MODEL = GamePlayerModel(
		userName = "Anonymous",
		clientId = "SomId",
		playerSymbol = BoardSymbols.OSymbol
	)

	val FAKE_ROOM_RESPONSE_MODEL = RoomResponseModel(
		roomId = "1234567890",
		rounds = 10
	)
}