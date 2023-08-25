package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class ReceiveEventsTypes(val type: String) {
    MESSAGE_TYPE("MESSAGE"),
    GAME_STATE_TYPE("GAME")
}