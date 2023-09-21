package com.eva.androidtictactoe.presentation.screens.feature_room

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.presentation.screens.feature_room.composable.RoomJoinDialog
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.RoomInteractionEvents
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.VerifyRoomState
import com.eva.androidtictactoe.presentation.utils.LocalSnackBarHostState
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.HugwaFontFamily
import com.eva.androidtictactoe.ui.theme.KgShadowFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyRoomScreen(
	state: VerifyRoomState,
	onRoomEvents: (RoomInteractionEvents) -> Unit,
	modifier: Modifier = Modifier,
	navigation: (@Composable () -> Unit)? = null,
	onCreateRedirect: () -> Unit,
	snackBarHostState: SnackbarHostState = LocalSnackBarHostState.current
) {
	if (state.showDialog) {
		RoomJoinDialog(
			message = state.message,
			model = state.response,
			onConfirm = { roomId ->
				onRoomEvents(RoomInteractionEvents.OnDialogConfirm(roomId))
			},
			onDismiss = { onRoomEvents(RoomInteractionEvents.OnDialogToggle) },
		)
	}


	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = stringResource(id = R.string.join_room_route_title),
						style = MaterialTheme.typography.titleLarge
							.copy(fontFamily = HugwaFontFamily)
					)
				},
				navigationIcon = navigation ?: {},
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = MaterialTheme.colorScheme.surfaceVariant,
					titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
				)
			)
		},
		snackbarHost = { SnackbarHost(snackBarHostState) })
	{ scPadding ->
		Column(
			modifier = modifier
				.padding(scPadding)
				.fillMaxSize()
				.padding(all = dimensionResource(id = R.dimen.scaffold_horizontal_padding)),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(modifier = Modifier.weight(.5f))
			Image(
				painter = painterResource(id = R.drawable.ic_handshake),
				contentDescription = stringResource(id = R.string.handshake_icon_content_desc),
				colorFilter = ColorFilter
					.tint(color = MaterialTheme.colorScheme.onPrimaryContainer),
				modifier = Modifier.padding(vertical = 10.dp)
			)
			Spacer(modifier = Modifier.height(20.dp))
			OutlinedTextField(
				value = state.roomId,
				textStyle = MaterialTheme.typography.bodyMedium,
				onValueChange = {
					onRoomEvents(RoomInteractionEvents.OnValueChange(it))
				},
				label = {
					Text(text = stringResource(id = R.string.verify_room_text_field_label))
				},
				placeholder = {
					Text(text = stringResource(id = R.string.room_placeholder))
				},
				keyboardOptions = KeyboardOptions(
					autoCorrect = false, keyboardType = KeyboardType.Ascii
				),
				maxLines = 1,
				singleLine = true,
				modifier = Modifier.fillMaxWidth(),
				shape = MaterialTheme.shapes.medium
			)
			Spacer(modifier = Modifier.height(20.dp))
			Text(
				text = stringResource(id = R.string.join_room_subtitle),
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				modifier = Modifier.padding(4.dp),
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.bodyMedium
			)
			Text(
				text = stringResource(id = R.string.join_room_info_text),
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				modifier = Modifier.padding(4.dp),
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.bodySmall
			)
			Spacer(modifier = Modifier.weight(1f))
			Button(
				onClick = { onRoomEvents(RoomInteractionEvents.RoomDataRequest) },
				modifier = Modifier
					.fillMaxWidth()
					.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
				shape = MaterialTheme.shapes.medium,
				enabled = !state.isLoading,
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.primary,
					contentColor = MaterialTheme.colorScheme.onPrimary
				)
			) {
				Text(
					text = stringResource(id = R.string.verify_room_button_text),
					style = MaterialTheme.typography.titleMedium
						.copy(fontFamily = KgShadowFontFamily)
				)
			}
			Spacer(modifier = Modifier.padding(vertical = 4.dp))
			FilledTonalButton(
				onClick = onCreateRedirect,
				colors = ButtonDefaults.filledTonalButtonColors(
					contentColor = MaterialTheme.colorScheme.onSurfaceVariant
				),
				modifier = Modifier
					.fillMaxWidth()
					.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
				shape = MaterialTheme.shapes.medium,
			) {
				Text(
					text = stringResource(id = R.string.verify_room_button_secondary_text),
					style = MaterialTheme.typography.bodyMedium
						.copy(fontFamily = KgShadowFontFamily)
				)
			}
			Spacer(
				modifier = Modifier.height(
					height = dimensionResource(id = R.dimen.room_screens_default_bottom_spacer)
				)
			)
		}
	}
}


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun VerifyRoomScreenPreview() {
	AndroidTicTacToeTheme {
		VerifyRoomScreen(
			state = VerifyRoomState(),
			onCreateRedirect = {},
			onRoomEvents = {},
		)
	}
}