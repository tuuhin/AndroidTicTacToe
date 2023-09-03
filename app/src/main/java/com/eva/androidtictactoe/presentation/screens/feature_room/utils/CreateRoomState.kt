package com.eva.androidtictactoe.presentation.screens.feature_room.utils

import com.eva.androidtictactoe.domain.model.RoomResponseModel

data class CreateRoomState(
	val showDialog: Boolean = false,
	val response: RoomResponseModel? = null,
	val boardCount: String = "1",
	val isLoading: Boolean = false
)