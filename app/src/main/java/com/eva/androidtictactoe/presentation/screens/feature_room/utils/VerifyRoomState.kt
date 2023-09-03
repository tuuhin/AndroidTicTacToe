package com.eva.androidtictactoe.presentation.screens.feature_room.utils

import com.eva.androidtictactoe.domain.model.RoomResponseModel

data class VerifyRoomState(
	val showDialog: Boolean = false,
	val response: RoomResponseModel? = null,
	val roomId: String = "",
	val isLoading: Boolean = false,
	val message: String = ""
)