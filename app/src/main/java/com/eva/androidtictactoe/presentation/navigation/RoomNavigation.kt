package com.eva.androidtictactoe.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.eva.androidtictactoe.presentation.composables.ArrowBackButton
import com.eva.androidtictactoe.presentation.composables.sharedKoinViewModel
import com.eva.androidtictactoe.presentation.screens.feature_room.CreateRoomScreen
import com.eva.androidtictactoe.presentation.screens.feature_room.OnBoardingScreen
import com.eva.androidtictactoe.presentation.screens.feature_room.PlayerRoomViewModel
import com.eva.androidtictactoe.presentation.screens.feature_room.VerifyRoomScreen
import com.eva.androidtictactoe.presentation.utils.LocalSnackBarHostState
import com.eva.androidtictactoe.presentation.utils.UiEvents

fun NavGraphBuilder.roomNavigation(
	navController: NavController,
	onJoinRedirect: () -> Unit,
	onCreateRedirect: () -> Unit,
	onGameScreen: (String?) -> Unit,
) {
	navigation(route = Screens.RoomRoute.route,
		startDestination = Screens.BoardingRoute.route,
		enterTransition = {
			slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
		},
		exitTransition = {
			slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
		}) {

		composable(route = Screens.BoardingRoute.route) { entry ->

			val viewModel = entry.sharedKoinViewModel<PlayerRoomViewModel>(navController)
			val userName by viewModel.userName.collectAsStateWithLifecycle()

			OnBoardingScreen(
				userName = userName,
				onUserNameEvents = viewModel::onUserNameEvents,
				onJoinRedirect = onJoinRedirect,
				onCreateRedirect = onCreateRedirect
			)
		}
		composable(route = Screens.CreateRoomRoute.route) { entry ->

			val snackBarHostState = LocalSnackBarHostState.current

			val viewModel = entry.sharedKoinViewModel<PlayerRoomViewModel>(navController)
			val state by viewModel.createRoomState.collectAsStateWithLifecycle()


			LaunchedEffect(Unit) {
				viewModel.uiEvents.collect { event ->
					when (event) {
						is UiEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
						is UiEvents.Navigate -> onJoinRedirect()
					}
				}
			}

			CreateRoomScreen(
				navigation = { ArrowBackButton(navController = navController) },
				onJoinRedirect = onJoinRedirect,
				createRoomState = state,
				onCreateRoomEvents = viewModel::onCreateRoomEvents,
			)
		}
		composable(route = Screens.JoinRoomRoute.route) { entry ->

			val snackBarHostState = LocalSnackBarHostState.current

			val viewModel = entry.sharedKoinViewModel<PlayerRoomViewModel>(navController)
			val state by viewModel.checkRoomState.collectAsStateWithLifecycle()

			LaunchedEffect(Unit) {
				viewModel.uiEvents.collect { event ->
					when (event) {
						is UiEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
						is UiEvents.Navigate -> onGameScreen(event.route)
					}
				}
			}

			VerifyRoomScreen(
				navigation = { ArrowBackButton(navController = navController) },
				onCreateRedirect = onCreateRedirect,
				state = state,
				onRoomEvents = viewModel::onCheckRoomEvents,
			)
		}
	}
}