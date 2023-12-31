package com.eva.androidtictactoe.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigationGraph(
	modifier: Modifier = Modifier,
) {
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = Screens.RoomRoute.route,
		modifier = modifier
	) {
		roomNavigation(
			navController = navController,
			onJoinRedirect = {
				navController.navigate(Screens.JoinRoomRoute.route) {
					launchSingleTop = true
					navController.previousBackStackEntry?.destination?.route?.let { route ->
						if (route == Screens.JoinRoomRoute.route) {
							popUpTo(route)
						}
					}
				}
			},
			onCreateRedirect = {
				navController.navigate(Screens.CreateRoomRoute.route) {
					launchSingleTop = true
					navController.previousBackStackEntry?.destination?.route?.let { route ->
						if (route == Screens.CreateRoomRoute.route) {
							popUpTo(route)
						}
					}
				}
			},
			onGameScreen = { roomId ->
				val destination =
					roomId?.let { id -> Screens.GameScreenWithRoomId(roomId = id).route }
						?: Screens.AnonymousGameScreen.route
				navController.navigate(destination)
			},
		)
		composable(
			route = Screens.GameScreenWithParam.route,
			arguments = listOf(
				navArgument(ScreenParameters.ROOM_CODE_PARAMS) {
					type = NavType.StringType
					nullable = true
				},
			),
			enterTransition = {
				slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left) +
						expandIn(expandFrom = Alignment.Center)
			},
			exitTransition = {
				slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left) +
						shrinkOut(shrinkTowards = Alignment.Center)
			},
		) { entry ->
			GameRoute(
				navController = navController,
				entry = entry,
			)
		}
	}
}
