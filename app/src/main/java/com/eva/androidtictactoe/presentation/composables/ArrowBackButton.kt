package com.eva.androidtictactoe.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ArrowBackButton(
	navController: NavController,
	modifier: Modifier = Modifier,
	colors: IconButtonColors = IconButtonDefaults.iconButtonColors()
) {
	if (navController.previousBackStackEntry != null)
		IconButton(
			onClick = { navController.navigateUp() },
			modifier = modifier,
			colors = colors
		) {
			Icon(
				imageVector = Icons.Default.ArrowBack,
				contentDescription = "Back Arrow"
			)
		}
}