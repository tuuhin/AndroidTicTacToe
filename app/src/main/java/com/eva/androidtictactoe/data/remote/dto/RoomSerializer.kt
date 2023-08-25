package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomSerializer(
    @SerialName("room") val room: String,
    @SerialName("no_of_rounds") val rounds: Int = 1
)