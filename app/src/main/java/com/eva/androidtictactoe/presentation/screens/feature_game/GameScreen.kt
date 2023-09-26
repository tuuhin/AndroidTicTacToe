package com.eva.androidtictactoe.presentation.screens.feature_game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.BoardPosition
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.BoardGameWrapper
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.GameInfoHeader
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.ServerMessageBox
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.TicTacToeBoard
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.WaitingForPlayer
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.HugwaFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
	message: String,
	board: BoardGameModel,
	onBoardPositions: (BoardPosition) -> Unit,
	modifier: Modifier = Modifier,
	navigation: (@Composable () -> Unit)? = null,
) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = stringResource(id = R.string.game_screen_title),
						style = MaterialTheme.typography.titleLarge
							.copy(fontFamily = HugwaFontFamily)
					)
				},
				navigationIcon = navigation ?: {},
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					titleContentColor = MaterialTheme.colorScheme.inverseSurface,
					containerColor = MaterialTheme.colorScheme.inverseOnSurface
				)
			)
		}
	) { scPadding ->
		BoardGameWrapper(
			board = board,
			waiting = {
				WaitingForPlayer(
					modifier = Modifier.fillMaxSize()
				)
			},
			game = {
				Spacer(modifier = Modifier.height(20.dp))
				board.player?.let player@{ player ->
					board.opponent?.let opponent@{ opponent ->
						GameInfoHeader(
							player = player,
							opponent = opponent,
							boardCount = board.game.boardCount,
							roundNumber = board.game.currentBoardNumber,
							modifier = Modifier.fillMaxWidth()
						)
					}
					Spacer(modifier = Modifier.height(20.dp))
					TicTacToeBoard(
						onTap = onBoardPositions,
						playerSymbols = board.player.symbol,
						board = board.game.board.boardFace,
						modifier = Modifier
							.fillMaxWidth(.75f)
							.align(Alignment.CenterHorizontally)
					)
				}
				Spacer(modifier = Modifier.height(20.dp))
				ServerMessageBox(
					messages = message,
					modifier = Modifier.fillMaxWidth()
				)
			},
			modifier = modifier
				.fillMaxSize()
				.padding(scPadding)
				.padding(all = dimensionResource(id = R.dimen.scaffold_horizontal_padding))
		)
	}
}

class BoardPreviewParams : CollectionPreviewParameterProvider<BoardGameModel>(
	listOf(
		FakePreview.FAKE_UN_INITIALIZE_GAME,
		FakePreview.FAKE_GOING_GAME
	)
)


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GameScreenPreview(
	@PreviewParameter(BoardPreviewParams::class)
	board: BoardGameModel
) {
	AndroidTicTacToeTheme {
		GameScreen(
			onBoardPositions = {},
			board = board,
			message = "Secret message",
		)
	}
}