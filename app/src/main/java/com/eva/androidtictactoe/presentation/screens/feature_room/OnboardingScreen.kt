package com.eva.androidtictactoe.presentation.screens.feature_room

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.presentation.screens.feature_room.composable.UserNameWarningDialog
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.UserNameEvents
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.HugwaFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
	userName: String,
	onUserNameEvents: (UserNameEvents) -> Unit,
	onJoinRedirect: () -> Unit,
	onCreateRedirect: () -> Unit,
	modifier: Modifier = Modifier,
	navigation: (@Composable () -> Unit)? = null
) {
	var showDialog by remember { mutableStateOf(false) }
	val focusRequester = remember { FocusRequester() }

	if (showDialog) {
		UserNameWarningDialog(
			onDismiss = { showDialog = !showDialog },
			onConfirm = {
				showDialog = !showDialog
				focusRequester.requestFocus()
			}
		)
	}

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = stringResource(id = R.string.game_name),
						modifier = Modifier.padding(vertical = 12.dp),
						style = MaterialTheme.typography.titleLarge
							.copy(fontFamily = HugwaFontFamily),
					)
				},
				navigationIcon = navigation ?: {},
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
					containerColor = MaterialTheme.colorScheme.surfaceVariant
				)
			)
		}
	) { scPadding ->

		Column(
			modifier
				.padding(scPadding)
				.fillMaxSize()
				.padding(dimensionResource(id = R.dimen.scaffold_horizontal_padding)),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(modifier = Modifier.weight(1f))
			Box(
				modifier = Modifier
					.fillMaxWidth(.75f)
					.aspectRatio(1f)
					.clip(MaterialTheme.shapes.medium)
					.background(MaterialTheme.colorScheme.primaryContainer),
				contentAlignment = Alignment.Center
			) {
				Image(
					painter = painterResource(id = R.drawable.ic_tic_tac_toe),
					contentDescription = "Tic Tac Toe Game",
					colorFilter = ColorFilter
						.tint(MaterialTheme.colorScheme.onPrimaryContainer),
					modifier = Modifier.padding(20.dp)
				)
			}
			Text(
				text = stringResource(id = R.string.game_extra_text),
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.secondary,
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(vertical = 8.dp)
			)
			Spacer(modifier = Modifier.weight(1f))
			OutlinedTextField(
				value = userName,
				textStyle = MaterialTheme.typography.bodyMedium,
				onValueChange = {
					onUserNameEvents(UserNameEvents.OnUserNameChange(it))
				},
				label = { Text(text = "User Name") },
				placeholder = { Text(text = "Player1") },
				leadingIcon = {
					Icon(
						imageVector = Icons.Outlined.Person,
						contentDescription = "Person's Icon"
					)
				},
				keyboardOptions = KeyboardOptions(
					capitalization = KeyboardCapitalization.Words,
					autoCorrect = false,
					keyboardType = KeyboardType.Ascii,
					imeAction = ImeAction.Done
				),
				keyboardActions = KeyboardActions(
					onDone = {
						defaultKeyboardAction(ImeAction.Done)
						onUserNameEvents(UserNameEvents.OnNameChangeConfirm)
					},
				),
				maxLines = 1,
				singleLine = true,
				shape = MaterialTheme.shapes.medium,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(focusRequester),
			)
			Divider(
				modifier = Modifier.padding(vertical = 12.dp),
				color = MaterialTheme.colorScheme.outline
			)
			Button(
				onClick = {
					when {
						userName.isEmpty() -> showDialog = true
						else -> onJoinRedirect()
					}
				},
				modifier = Modifier
					.fillMaxWidth()
					.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
				elevation = ButtonDefaults
					.elevatedButtonElevation(defaultElevation = 2.dp),
				shape = MaterialTheme.shapes.medium
			) {
				Text(
					text = "Join Now",
					style = MaterialTheme.typography.bodyLarge
				)
			}
			Spacer(modifier = Modifier.height(8.dp))
			FilledTonalButton(
				onClick = {
					when {
						userName.isEmpty() -> showDialog = true
						else -> onCreateRedirect()
					}
				},
				modifier = Modifier
					.fillMaxWidth()
					.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
				shape = MaterialTheme.shapes.medium
			) {
				Text(
					text = "Create a Room",
					style = MaterialTheme.typography.bodyLarge
				)
			}
		}
	}
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun OnBoardingScreenPreview() {
	AndroidTicTacToeTheme {
		OnBoardingScreen(
			userName = "",
			onUserNameEvents = {},
			onJoinRedirect = {},
			onCreateRedirect = {},
		)
	}
}