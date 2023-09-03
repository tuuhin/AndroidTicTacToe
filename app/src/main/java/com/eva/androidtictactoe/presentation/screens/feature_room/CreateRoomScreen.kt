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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.presentation.screens.feature_room.composable.RoomCreateDialog
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.CreateRoomState
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.RoomInteractionEvents
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.presentation.utils.LocalSnackBarHostState
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomScreen(
	modifier: Modifier = Modifier,
	navigation: (@Composable () -> Unit)? = null,
	createRoomState: CreateRoomState,
	onCreateRoomEvents: (RoomInteractionEvents) -> Unit,
	onJoinRedirect: () -> Unit,
	snackBarHostState: SnackbarHostState = LocalSnackBarHostState.current
) {
	if (createRoomState.showDialog) {
		RoomCreateDialog(
			responseModel = createRoomState.response,
			onConfirm = { roomId -> onCreateRoomEvents(RoomInteractionEvents.OnDialogConfirm(roomId)) },
			onDismiss = { onCreateRoomEvents(RoomInteractionEvents.OnDialogToggle) }
		)
	}
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = stringResource(id = R.string.create_room_route_title)) },
				navigationIcon = navigation ?: {},
				colors = TopAppBarDefaults
					.centerAlignedTopAppBarColors(
						containerColor = MaterialTheme.colorScheme.surfaceVariant,
						titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
					)
			)
		},
		snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
	) { scPadding ->
		Column(
			modifier = modifier
				.padding(scPadding)
				.fillMaxSize()
				.padding(all = dimensionResource(id = R.dimen.scaffold_horizontal_padding)),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(modifier = Modifier.weight(.5f))
			Image(
				painter = painterResource(id = R.drawable.ic_wizard_hat),
				contentDescription = "Handshake Icon",
				colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer),
				modifier = Modifier.padding(vertical = 10.dp)
			)
			Spacer(modifier = Modifier.height(20.dp))
			OutlinedTextField(
				value = createRoomState.boardCount,
				textStyle = MaterialTheme.typography.bodyMedium,
				onValueChange = { newCount ->
					onCreateRoomEvents(RoomInteractionEvents.OnValueChange(newCount))
				},
				label = { Text(text = "Number of Boards") },
				placeholder = { Text(text = "1") },
				keyboardOptions = KeyboardOptions(
					autoCorrect = false,
					keyboardType = KeyboardType.Number
				),
				supportingText = { Text(text = "The default value for board count is 1") },
				maxLines = 1,
				singleLine = true,
				modifier = Modifier.fillMaxWidth(),
				shape = MaterialTheme.shapes.medium
			)
			Spacer(modifier = Modifier.height(20.dp))
			Text(
				text = stringResource(id = R.string.create_room_subtitle),
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				modifier = Modifier.padding(4.dp),
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.bodyMedium
			)
			Text(
				text = stringResource(id = R.string.create_room_extra),
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				modifier = Modifier.padding(4.dp),
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.bodySmall
			)
			Spacer(modifier = Modifier.weight(1f))
			Button(
				onClick = { onCreateRoomEvents(RoomInteractionEvents.RoomDataRequest) },
				modifier = Modifier
					.fillMaxWidth()
					.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
				shape = MaterialTheme.shapes.medium,
				enabled = !createRoomState.isLoading
			) {
				Text(
					text = "Create Room",
					style = MaterialTheme.typography.bodyLarge
				)
			}
			Spacer(modifier = Modifier.padding(vertical = 4.dp))
			TextButton(
				onClick = onJoinRedirect,
				colors = ButtonDefaults.textButtonColors(
					contentColor = MaterialTheme.colorScheme.onSurfaceVariant
				)
			) {
				Text(
					text = buildAnnotatedString {
						withStyle(
							style = SpanStyle(
								fontSize = MaterialTheme.typography.bodySmall.fontSize,
								color = MaterialTheme.colorScheme.onSurfaceVariant
							)
						) { append("Already have a code") }
						withStyle(
							style = SpanStyle(
								textDecoration = TextDecoration.Underline,
								color = MaterialTheme.colorScheme.secondary,
								fontSize = MaterialTheme.typography.bodyMedium.fontSize
							)
						) { append("Join") }
					}
				)
			}
		}
	}
}


class CreateRoomDialogPreviewParams : CollectionPreviewParameterProvider<CreateRoomState>(
	listOf(
		CreateRoomState(),
		CreateRoomState(
			showDialog = true,
			response = FakePreview.FAKE_ROOM_RESPONSE_MODEL
		)
	)
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun CreateRoomScreenPreview(
	@PreviewParameter(CreateRoomDialogPreviewParams::class)
	state: CreateRoomState
) {
	AndroidTicTacToeTheme {
		CreateRoomScreen(
			onJoinRedirect = {},
			createRoomState = state,
			onCreateRoomEvents = {},
		)
	}
}