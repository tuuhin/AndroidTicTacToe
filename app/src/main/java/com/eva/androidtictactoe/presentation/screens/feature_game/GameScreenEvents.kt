package com.eva.androidtictactoe.presentation.screens.feature_game

sealed interface GameAchievementEvents {

	object AcceptQuitGameDialog : GameAchievementEvents

}

sealed interface GameBackHandlerEvents {

	object OnBackButtonPressed : GameBackHandlerEvents

	object ToggleCancelGameDialog : GameBackHandlerEvents

	object CancelGame : GameBackHandlerEvents
}