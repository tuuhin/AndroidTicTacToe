package com.eva.androidtictactoe.data.remote.dto


import com.eva.androidtictactoe.domain.model.GameSymbols
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameRoomDto(
	@SerialName("board_count") val boardCount: Int = 0,
	@SerialName("board_layout") val boardLayout: List<List<Char>> = emptyBoardLayout(),
	@SerialName("is_anonymous") val isAnonymous: Boolean = false,
	@SerialName("room_id") val room: String = "",
	@SerialName("is_draw") val isDraw: Boolean = false
) {
	companion object {
		fun emptyBoardLayout() = List(3) {
			List(3) { GameSymbols.Blank.symbol }
		}
	}
}