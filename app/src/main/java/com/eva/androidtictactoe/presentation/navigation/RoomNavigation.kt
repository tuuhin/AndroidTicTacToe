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
import com.eva.androidtictactoe.presentation.screens.feature_room.CreateRoomScreen
import com.eva.androidtictactoe.presentation.screens.feature_room.JoinRoomScreen
import com.eva.androidtictactoe.presentation.screens.feature_room.OnBoardingScreen
import com.eva.androidtictactoe.presentation.screens.feature_room.PlayerRoomViewModel
import com.eva.androidtictactoe.presentation.utils.LocalSnackBarHostState
import com.eva.androidtictactoe.presentation.utils.UiEvents
import com.eva.androidtictactoe.presentation.utils.sharedKoinViewModel
import kotlinx.coroutines.flow.onEach

fun NavGraphBuilder.roomNavigation(
	navController: NavController
) {
	navigation(
		route = Screens.RoomRoute.route,
		startDestination = Screens.BoardingRoute.route,
		enterTransition = {
			slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
		},
		exitTransition = {
			slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
		}
	) {

		composable(route = Screens.BoardingRoute.route) { entry ->

			val viewModel = entry.sharedKoinViewModel<PlayerRoomViewModel>(navController)
			val userName by viewModel.userName.collectAsStateWithLifecycle()

			OnBoardingScreen(
				userName = userName,
				onUserNameChange = { name -> viewModel.onUserNameChange(name) },
				onDone = viewModel::onUserNameChangeDone,
				onRoomJoin = { navController.navigate(route = Screens.JoinRoomRoute.route) },
				onCreateRoom = { navController.navigate(route = Screens.CreateRoomRoute.route) }
			)
		}
		composable(route = Screens.CreateRoomRoute.route) { entry ->

			val snackBarHostState = LocalSnackBarHostState.current

			val viewModel = entry.sharedKoinViewModel<PlayerRoomViewModel>(navController)
			val boardCount by viewModel.boardCount.collectAsStateWithLifecycle()


			LaunchedEffect(viewModel) {
				viewModel.uiEvents.onEach { event ->
					when (event) {
						is UiEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
						else -> {}
					}
				}
			}

			CreateRoomScreen(
				navigation = { ArrowBackButton(navController = navController) },
				onJoinRoute = {
					navController.navigate(Screens.JoinRoomRoute.route) {
						launchSingleTop = true
						navController.previousBackStackEntry?.destination?.route
							?.let { route ->
								if (route == Screens.CreateRoomRoute.route) {
									popUpTo(route)
								}
							}
					}
				},
				onCreate = { /*TODO*/ },
				boardCount = boardCount,
				onBoardCountChange = viewModel::onBoardCountChange
			)
		}
		composable(route = Screens.JoinRoomRoute.route) { entry ->

			val snackBarHostState = LocalSnackBarHostState.current

			val viewModel = entry.sharedKoinViewModel<PlayerRoomViewModel>(navController)
			val roomId by viewModel.roomId.collectAsStateWithLifecycle()

			LaunchedEffect(viewModel) {
				viewModel.uiEvents.onEach { event ->
					when (event) {
						is UiEvents.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
						else -> {}
					}
				}
			}
			JoinRoomScreen(
				navigation = { ArrowBackButton(navController = navController) },
				onVerify = { /*TODO*/ },
				onCreateRoom = {
					navController.navigate(Screens.CreateRoomRoute.route) {
						navController.previousBackStackEntry?.destination?.route
							?.let { route ->
								if (route == Screens.CreateRoomRoute.route) {
									popUpTo(route)
								}
							}
						launchSingleTop = true
					}
				}, roomId = roomId,
				onRoomIdChange = viewModel::onRoomIdChange
			)
		}
	}
}