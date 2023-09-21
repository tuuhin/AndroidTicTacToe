package com.eva.androidtictactoe.presentation.screens.feature_game.util

import com.eva.androidtictactoe.domain.model.BoardPosition

sealed interface GameScreenEvents {
	object BackButtonPressed : GameScreenEvents
	data class OnBoardPositionSelect(val position: BoardPosition) : GameScreenEvents

}