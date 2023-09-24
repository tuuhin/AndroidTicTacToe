package com.eva.androidtictactoe.data.remote.serializers

import com.eva.androidtictactoe.data.remote.dto.ReceiveEventsDto
import com.eva.androidtictactoe.data.remote.dto.ReceiveEventsTypes
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ReceiveEventsSerializer :
	JsonContentPolymorphicSerializer<ReceiveEventsDto>(ReceiveEventsDto::class) {
	override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ReceiveEventsDto> {
		return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
			ReceiveEventsTypes.MESSAGE_TYPE.type -> ReceiveEventsDto.ReceivedMessage.serializer()
			ReceiveEventsTypes.GAME_STATE_TYPE.type -> ReceiveEventsDto.ReceivedGameData.serializer()
			ReceiveEventsTypes.ACHIEVEMENT_TYPE.type -> ReceiveEventsDto.GameAchievementState.serializer()
			else -> throw Exception("Unknown key 'type' not found")
		}
	}
}