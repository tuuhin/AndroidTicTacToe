package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.GamePlayerModel

@Composable
fun BoardGameWrapper(
	board: BoardGameModel,
	onWaiting: @Composable (ColumnScope.() -> Unit),
	showBoard: @Composable (ColumnScope.(GamePlayerModel, GamePlayerModel) -> Unit),
	modifier: Modifier = Modifier,
	onNotReady: @Composable (ColumnScope.() -> Unit) = {},
) {
	Column(
		modifier = modifier
	) {
		when {
			board.player == null || board.opponent == null -> onWaiting()
			!board.isReady -> onNotReady()
			else -> showBoard(board.player, board.opponent)
		}
	}
}