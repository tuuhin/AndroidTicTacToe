package com.eva.androidtictactoe.data.remote.dto

import com.eva.androidtictactoe.data.remote.serializers.SendEventsSerializer
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(SendEventsSerializer::class)
@OptIn(ExperimentalSerializationApi::class)
sealed interface SendEventsDto {
	@Serializable
	data class SendGameData(
		@EncodeDefault @SerialName("type") val type: String = SendEventTypes.GAME_STATE_TYPE.type,
		@SerialName("data") val data: SendGameDataDto
	) : SendEventsDto
}
