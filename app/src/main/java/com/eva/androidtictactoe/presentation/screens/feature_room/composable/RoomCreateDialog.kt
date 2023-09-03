package com.eva.androidtictactoe.presentation.screens.feature_room.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.RoomResponseModel

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
			Button(
				onClick = { responseModel?.roomId?.let(onConfirm) },
				enabled = responseModel?.roomId != null
			) {
				Text(text = "Join", style = MaterialTheme.typography.bodyMedium)
			}
		},
		title = { Text(text = stringResource(id = R.string.create_room_dialog_title)) },
		text = {
			responseModel?.roomId?.let { roomId ->
				Text(
					text = buildAnnotatedString {
						append("Your request to create a new room is successful.")
						append("New Room Id ")
						withStyle(
							style = SpanStyle(
								color = MaterialTheme.colorScheme.onPrimaryContainer,
								fontWeight = FontWeight.SemiBold,
								textDecoration = TextDecoration.Underline
							)
						) {
							append(roomId)
						}
						append("is copied to clipboard.")
						append("Continue with joining")
					}
				)
			} ?: Text(text = stringResource(id = R.string.request_failed_dialog_text))
		},
		textContentColor = MaterialTheme.colorScheme.secondary,
		titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
		shape = MaterialTheme.shapes.medium,
		modifier = modifier
	)
}