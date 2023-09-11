package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardGameRoomDataDto(

	@SerialName("x_player")
	val playerX: GamePlayerDto? = null,

	@SerialName("o_player")
	val playerO: GamePlayerDto? = null,

	@SerialName("room")
	val board: GameRoomDto = GameRoomDto(),

	@SerialName("is_ready")
	val didAllJoined: Boolean = false
)
