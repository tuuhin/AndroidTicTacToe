package com.eva.androidtictactoe.presentation.screens.feature_room.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.eva.androidtictactoe.R

@Composable
fun UserNameWarningDialog(
	modifier: Modifier = Modifier,
	onDismiss: () -> Unit,
	onConfirm: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			Button(onClick = onConfirm) {
				Text(text = stringResource(id = R.string.dialog_confirm_text))
			}
		},
		title = { Text(text = "UserName Missing") },
		text = {
			Text(text = stringResource(id = R.string.no_username_dialog_text))
		},
		textContentColor = MaterialTheme.colorScheme.secondary,
		titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
		shape = MaterialTheme.shapes.medium,
		modifier = modifier
	)
}