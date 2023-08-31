package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifiedRoomSerializer(
	@SerialName("message") val message: String,
	@SerialName("room") val room: RoomSerializer
)
