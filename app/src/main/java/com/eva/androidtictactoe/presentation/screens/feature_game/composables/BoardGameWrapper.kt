package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eva.androidtictactoe.domain.model.BoardGameModel

@Composable
fun BoardGameWrapper(
	board: BoardGameModel,
	waiting: @Composable (ColumnScope.() -> Unit),
	game: @Composable (ColumnScope.() -> Unit),
	modifier: Modifier = Modifier,
	notReady: @Composable (ColumnScope.() -> Unit) = {},
) {
	Column(
		modifier = modifier
	) {
		when {
			board.player == null || board.opponent == null -> waiting()
			!board.isReady -> notReady()
			else -> game()
		}
	}
}