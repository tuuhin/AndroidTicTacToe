package com.eva.androidtictactoe.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(controller: NavController): T {
	val parent = destination.parent?.route ?: return koinViewModel()
	val viewModelStoreOwner = remember(this) {
		controller.getBackStackEntry(parent)
	}
	return koinViewModel(viewModelStoreOwner = viewModelStoreOwner)
}