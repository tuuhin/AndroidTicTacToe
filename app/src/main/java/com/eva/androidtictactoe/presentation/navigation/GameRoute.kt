package com.eva.androidtictactoe.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.eva.androidtictactoe.presentation.composables.ArrowBackButton
import com.eva.androidtictactoe.presentation.screens.feature_game.GameBackHandlerEvents
import com.eva.androidtictactoe.presentation.screens.feature_game.GameScreen
import com.eva.androidtictactoe.presentation.screens.feature_game.GameScreenViewModel
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.CancelGameDialog
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.FinishGameDialog
import com.eva.androidtictactoe.presentation.utils.LocalSnackBarHostState
import com.eva.androidtictactoe.presentation.utils.UiEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameRoute(
	navController: NavController,
	entry: NavBackStackEntry,
	snackBarHostState: SnackbarHostState = LocalSnackBarHostState.current,
) {
	val viewModel = koinViewModel<GameScreenViewModel>()

	val gameBoard by viewModel.gameEvents.collectAsStateWithLifecycle()

	val serverMessages by viewModel.serverMessages.collectAsStateWithLifecycle()

	val achievements by viewModel.achievements.collectAsStateWithLifecycle()

	val backHandlerState by viewModel.backHandlerState.collectAsStateWithLifecycle()

	LaunchedEffect(entry) {
		viewModel.connectionEventsError.collect { events ->
			when (events) {
				is UiEvents.ShowSnackBar -> snackBarHostState.showSnackbar(events.message)
				is UiEvents.Navigate -> {}
			}
		}
	}

	if (backHandlerState.showOnBackDialog) {
		CancelGameDialog(
			onBackEvents = viewModel::onBackHandlerEvents,
		)
	}

	if (achievements.showAchievement) {
		FinishGameDialog(
			state = achievements,
			onEvents = viewModel::onAchievementEvents,
		)
	}

	BackHandler(
		onBack = { viewModel.onBackHandlerEvents(GameBackHandlerEvents.OnBackButtonPressed) },
		enabled = !backHandlerState.isBackAllowed
	)

	GameScreen(
		navigation = { ArrowBackButton(navController = navController) },
		board = gameBoard,
		onBoardPositions = viewModel::onPositionSelect,
		message = serverMessages,
	)
}
