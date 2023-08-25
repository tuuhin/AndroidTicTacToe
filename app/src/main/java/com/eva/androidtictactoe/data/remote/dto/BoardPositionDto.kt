package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardPositionDto(
    @SerialName("x") val x: Int,
    @SerialName("y") val y: Int,
)