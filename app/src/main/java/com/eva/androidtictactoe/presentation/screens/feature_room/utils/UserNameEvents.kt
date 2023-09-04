package com.eva.androidtictactoe.presentation.screens.feature_room.utils

sealed interface UserNameEvents {
	data class OnUserNameChange(val value: String) : UserNameEvents
	object OnNameChangeConfirm : UserNameEvents
	object ToggleUserNameDialog : UserNameEvents
}