package com.eva.androidtictactoe.presentation.screens.feature_room.utils

sealed interface RoomInteractionEvents {

	data class OnValueChange(val count: String) : RoomInteractionEvents
	object OnDialogToggle : RoomInteractionEvents
	object RoomDataRequest : RoomInteractionEvents
	data class ToggleLoadingState(val state: Boolean) : RoomInteractionEvents

	data class OnDialogConfirm(val roomId: String) : RoomInteractionEvents
}