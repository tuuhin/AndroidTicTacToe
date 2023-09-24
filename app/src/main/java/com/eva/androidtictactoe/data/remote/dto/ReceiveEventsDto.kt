package com.eva.androidtictactoe.data.remote.dto

import com.eva.androidtictactoe.data.remote.serializers.ReceiveEventsSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(ReceiveEventsSerializer::class)
sealed interface ReceiveEventsDto {
	@Serializable
	data class ReceivedMessage(
		@SerialName("type") val type: String = ReceiveEventsTypes.MESSAGE_TYPE.type,
		@SerialName("result") val message: String
	) : ReceiveEventsDto

	@Serializable
	data class ReceivedGameData(
		@SerialName("type") val type: String = ReceiveEventsTypes.GAME_STATE_TYPE.type,
		@SerialName("result") val state: BoardGameRoomDataDto
	) : ReceiveEventsDto

	@Serializable
	data class GameAchievementState(
		@SerialName("type") val type: String = ReceiveEventsTypes.ACHIEVEMENT_TYPE.type,
		@SerialName("result") val result: GameAchievementDto
	) : ReceiveEventsDto
}
