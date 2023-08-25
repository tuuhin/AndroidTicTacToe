package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRoomSerializer(
   @SerialName("no_of_rounds") val rounds: Int = 1
)