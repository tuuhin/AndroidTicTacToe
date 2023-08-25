package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(ServerEventsSerializer::class)
sealed class ReceiveEventsDto {
    @Serializable
    data class ReceivedMessage(
        @SerialName("type") val type: String = ReceiveEventsTypes.MESSAGE_TYPE.type,
        @SerialName("result") val message: String
    ) : ReceiveEventsDto()

    @Serializable
    data class ReceivedGameData(
        @SerialName("type") val type: String = ReceiveEventsTypes.GAME_STATE_TYPE.type,
        @SerialName("result") val state: BoardGameReceiveDataDto
    ) : ReceiveEventsDto()
}

private object ServerEventsSerializer :
    JsonContentPolymorphicSerializer<ReceiveEventsDto>(ReceiveEventsDto::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ReceiveEventsDto> {
        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            ReceiveEventsTypes.MESSAGE_TYPE.type -> ReceiveEventsDto.ReceivedMessage.serializer()
            ReceiveEventsTypes.GAME_STATE_TYPE.type -> ReceiveEventsDto.ReceivedGameData.serializer()
            else -> throw Exception("Unknown Module: key 'type' not found or does not matches any module type")
        }
    }
}