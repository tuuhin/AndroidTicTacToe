package com.eva.androidtictactoe.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eva.androidtictactoe.domain.model.BoardSymbols
import com.eva.androidtictactoe.presentation.screens.feature_game.GameScreen
import com.eva.androidtictactoe.presentation.screens.feature_game.GameScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigationGraph(
	modifier: Modifier = Modifier
) {
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = Screens.RoomRoute.route,
		modifier = modifier
	) {
		roomNavigation(navController)
		composable(
			route = Screens.GameScreen.route,
			arguments = listOf(
				navArgument(ScreenParameters.ROOM_CODE_PARAMS) {
					type = NavType.StringType
					nullable = true
				},
			), enterTransition = {
				slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up) + expandIn()
			}, exitTransition = {
				slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down) + shrinkOut()
			}
		) { entry ->
			val roomId = entry.arguments?.getString(ScreenParameters.ROOM_CODE_PARAMS) ?: ""

			val viewModel = koinViewModel<GameScreenViewModel>()

			val boardState by viewModel.boardState.collectAsStateWithLifecycle()


			GameScreen(
				navigation = {
					IconButton(onClick = {}) {
						Icon(
							imageVector = Icons.Default.ArrowBack, contentDescription = "Back arrow"
						)
					}
				}, playerSymbols = BoardSymbols.XSymbol
			)
		}
	}
}
