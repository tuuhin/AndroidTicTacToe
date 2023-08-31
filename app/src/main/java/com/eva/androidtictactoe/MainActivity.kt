package com.eva.androidtictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.eva.androidtictactoe.presentation.navigation.AppNavigationGraph
import com.eva.androidtictactoe.presentation.utils.LocalSnackBarHostState
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme


class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			AndroidTicTacToeTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val snackBarHostState = remember { SnackbarHostState() }

					CompositionLocalProvider(
						LocalSnackBarHostState provides snackBarHostState
					) {
						AppNavigationGraph()
					}
				}
			}
		}
	}
}
