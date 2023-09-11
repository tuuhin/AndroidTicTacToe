package com.eva.androidtictactoe.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class SendEventTypes(val type: String) {
	GAME_STATE_TYPE(type = "GAME")
}