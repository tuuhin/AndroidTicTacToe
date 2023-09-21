package com.eva.androidtictactoe.presentation.screens.feature_room.composable

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.RoomResponseModel
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.KgShadowFontFamily

@Composable
fun RoomCreateDialog(
	onDismiss: () -> Unit,
	onConfirm: (String) -> Unit,
	modifier: Modifier = Modifier,
	responseModel: RoomResponseModel? = null,
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(
				onClick = { responseModel?.roomId?.let(onConfirm) },
				enabled = responseModel?.roomId != null,
				colors = ButtonDefaults
					.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
			) {
				Text(
					text = "Continue to Join",
					style = MaterialTheme.typography.bodyMedium
						.copy(fontFamily = KgShadowFontFamily)
				)
			}
		},
		title = {
			Text(
				text = stringResource(id = R.string.create_room_dialog_title),
				style = MaterialTheme.typography.titleLarge
			)
		},
		text = {
			responseModel?.roomId?.let { roomId ->
				Column(
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Text(
						text = stringResource(id = R.string.create_room_dialog_text),
						style = MaterialTheme.typography.bodyMedium
					)

					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.Center,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(
							text = stringResource(id = R.string.room_code),
							style = MaterialTheme.typography.titleMedium
						)
						Spacer(modifier = Modifier.width(8.dp))
						Box(
							modifier = Modifier
								.clip(MaterialTheme.shapes.small)
								.background(MaterialTheme.colorScheme.secondaryContainer),
							contentAlignment = Alignment.Center
						) {
							Text(
								text = roomId,
								modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
								style = MaterialTheme.typography.bodyMedium,
								color = MaterialTheme.colorScheme.onSecondaryContainer
							)
						}
					}
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

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun RoomCreateDialogPreview() {
	AndroidTicTacToeTheme {
		RoomCreateDialog(
			onDismiss = {},
			onConfirm = {},
			responseModel = FakePreview.FAKE_ROOM_RESPONSE_MODEL
		)
	}
}