package com.eva.androidtictactoe.presentation.screens.feature_room.utils

sealed interface RoomInteractionEvents {

	/**
	 * On text field value changed
	 * @param count The updated value that is used to update the state itself
	 */
	data class OnValueChange(val count: String) : RoomInteractionEvents

	/**
	 * Toggles the Dialog
	 */
	object OnDialogToggle : RoomInteractionEvents

	/**
	 * Triggers when the user request for the data
	 */
	object RoomDataRequest : RoomInteractionEvents

	/**
	 * Toggles the loading state ,during loading user should not click the button again
	 * @param state Denotes the current state if true the request is processing otherwise false
	 */
	data class ToggleLoadingState(val state: Boolean) : RoomInteractionEvents

	/**
	 * Dialog Confirm Event,triggered when a dialog is confirmed
	 * @param roomId The roomId received after a successful request
	 */
	data class OnDialogConfirm(val roomId: String) : RoomInteractionEvents
}