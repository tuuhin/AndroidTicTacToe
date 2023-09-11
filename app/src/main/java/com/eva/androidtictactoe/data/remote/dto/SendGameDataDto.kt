package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendGameDataDto(
	@SerialName("client_id") val clientId: String,
	@SerialName("position") val positionDto: BoardPositionDto
)