package com.eva.androidtictactoe.data.remote.serializers

import com.eva.androidtictactoe.data.remote.dto.SendEventTypes
import com.eva.androidtictactoe.data.remote.dto.SendEventsDto
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object SendEventsSerializer :
	JsonContentPolymorphicSerializer<SendEventsDto>(SendEventsDto::class) {
	override fun selectDeserializer(element: JsonElement): DeserializationStrategy<SendEventsDto> {
		return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
			SendEventTypes.GAME_STATE_TYPE.type -> SendEventsDto.SendGameData.serializer()
			else -> throw Exception("Unknown key 'type' not found")
		}
	}
}