package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.presentation.screens.feature_game.GameBackHandlerEvents
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.KgShadowFontFamily

@Composable
fun CancelGameDialog(
	onDismiss: () -> Unit,
	onConfirm: () -> Unit,
	modifier: Modifier = Modifier,
	onCancel: (() -> Unit)? = null,
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		dismissButton = {
			onCancel?.let { dismissCallback ->
				TextButton(
					onClick = dismissCallback,
					colors = ButtonDefaults.textButtonColors(
						contentColor = MaterialTheme.colorScheme.secondary
					)
				) {
					Text(text = stringResource(id = R.string.cancel_dialog_dismiss_button_text))
				}
			}
		},
		confirmButton = {
			Button(
				onClick = onConfirm,
				colors = ButtonDefaults.buttonColors(
					contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
					containerColor = MaterialTheme.colorScheme.primaryContainer
				)
			) {
				Text(
					text = stringResource(id = R.string.cancel_dialog_confirm_button_text),
					style = MaterialTheme.typography.bodyMedium
						.copy(fontFamily = KgShadowFontFamily)
				)
			}
		},
		title = {
			Text(
				text = stringResource(id = R.string.cancel_gama_dialog_title),
				style = MaterialTheme.typography.titleLarge
			)
		},
		text = {
			Text(
				text = stringResource(id = R.string.cancel_game_dialog_text),
				style = MaterialTheme.typography.bodyMedium
			)
		},
		textContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
		titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
		shape = MaterialTheme.shapes.large,
		modifier = modifier
	)
}


@Composable
fun CancelGameDialog(
	onBackEvents: (GameBackHandlerEvents) -> Unit,
	modifier: Modifier = Modifier,
) {
	CancelGameDialog(
		onDismiss = { onBackEvents(GameBackHandlerEvents.ToggleCancelGameDialog) },
		onCancel = { onBackEvents(GameBackHandlerEvents.ToggleCancelGameDialog) },
		onConfirm = { onBackEvents(GameBackHandlerEvents.CancelGame) },
		modifier = modifier
	)
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun CancelDialogPreview() {
	AndroidTicTacToeTheme {
		CancelGameDialog(
			onDismiss = { },
			onConfirm = { },
			onCancel = {}
		)
	}
}