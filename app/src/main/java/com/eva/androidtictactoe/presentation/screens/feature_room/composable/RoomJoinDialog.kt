package com.eva.androidtictactoe.presentation.screens.feature_room.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.RoomResponseModel

@Composable
fun RoomJoinDialog(
	message: String,
	onDismiss: () -> Unit,
	onConfirm: (String) -> Unit,
	modifier: Modifier = Modifier,
	model: RoomResponseModel? = null,
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			Button(
				onClick = { model?.roomId?.let(onConfirm) },
				enabled = model?.roomId != null
			) {
				Text(
					text = "Join Game",
					style = MaterialTheme.typography.bodyMedium
				)
			}
		},
		title = { Text(text = stringResource(id = R.string.verify_room_dialog_title)) },
		text = {
			Column {
				Text(
					text = message,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onTertiaryContainer
				)
				model?.let {
					Text(
						text = "Room Verified continue to join the game.",
						style = MaterialTheme.typography.bodyMedium
					)
				} ?: Text(
					text = stringResource(id = R.string.request_failed_dialog_text),
					style = MaterialTheme.typography.bodyMedium
				)
			}
		},
		textContentColor = MaterialTheme.colorScheme.secondary,
		titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
		shape = MaterialTheme.shapes.medium,
		modifier = modifier
	)
}

@Preview
@Composable
fun RoomJoinDialogPreview() {
	RoomJoinDialog(
		onDismiss = { },
		onConfirm = {},
		message = "Cannot create this room"
	)
}