package com.eva.androidtictactoe.presentation.screens.feature_room.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.RoomResponseModel
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.KgShadowFontFamily

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
			TextButton(
				onClick = { model?.roomId?.let(onConfirm) },
				enabled = model?.roomId != null,
				colors = ButtonDefaults
					.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
			) {
				Text(
					text = "Join Game",
					style = MaterialTheme.typography.bodyMedium
						.copy(fontFamily = KgShadowFontFamily)
				)
			}
		},
		title = {
			Text(
				text = stringResource(id = R.string.verify_room_dialog_title),
				style = MaterialTheme.typography.titleLarge
			)
		},
		text = {
			model?.let {
				Column(
					verticalArrangement = Arrangement.spacedBy(4.dp)
				) {
					Text(
						text = stringResource(id = R.string.verify_room_dialog_text),
						style = MaterialTheme.typography.bodyMedium
					)
					Text(
						text = message,
						style = MaterialTheme.typography.bodySmall,
						modifier = Modifier.align(Alignment.CenterHorizontally)
					)
				}
			} ?: Text(
				text = stringResource(id = R.string.request_failed_dialog_text),
				style = MaterialTheme.typography.bodyMedium
			)
		},
		textContentColor = MaterialTheme.colorScheme.secondary,
		titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
		shape = MaterialTheme.shapes.large,
		modifier = modifier
	)
}

@Preview
@Composable
fun RoomJoinDialogPreview() {
	AndroidTicTacToeTheme {
		RoomJoinDialog(
			onDismiss = { },
			onConfirm = {},
			message = "Room is join-able",
			model = FakePreview.FAKE_ROOM_RESPONSE_MODEL
		)
	}
}