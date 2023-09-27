package com.eva.androidtictactoe.data.remote.dto


import com.eva.androidtictactoe.domain.model.GameSymbols
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameRoomDto(

	@SerialName("board_count")
	val boardCount: Int = 1,

	@SerialName("current_board")
	val currentBoard: Int = 1,

	@SerialName("board_layout")
	val boardLayout: List<List<Char>> = emptyBoardLayout(),

	@SerialName("room_id")
	val room: String = "",

	@SerialName("is_draw")
	val isDraw: Boolean = false,

	@SerialName("winning_symbol")
	val winningSymbols: Char? = null,

	@SerialName("is_ready")
	val isReady: Boolean = false
) {
	companion object {
		fun emptyBoardLayout() = List(3) {
			List(3) { GameSymbols.Blank.symbol }
		}
	}
}