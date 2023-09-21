package com.eva.androidtictactoe.presentation.screens.feature_room

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.remember
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
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.ChangeUserNameState
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.UserNameEvents
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.HugwaFontFamily
import com.eva.androidtictactoe.ui.theme.KgShadowFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
	state: ChangeUserNameState,
	onUserNameEvents: (UserNameEvents) -> Unit,
	onJoinRedirect: () -> Unit,
	onCreateRedirect: () -> Unit,
	onAnonymousPlay: () -> Unit,
	modifier: Modifier = Modifier,
	navigation: (@Composable () -> Unit)? = null
) {
	val focusRequester = remember { FocusRequester() }

	if (state.showDialog) {
		UserNameWarningDialog(
			onDismiss = { onUserNameEvents(UserNameEvents.ToggleUserNameDialog) },
			onConfirm = {
				onUserNameEvents(UserNameEvents.ToggleUserNameDialog)
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
						style = MaterialTheme.typography.titleLarge
							.copy(fontFamily = HugwaFontFamily),
					)
				},
				navigationIcon = navigation ?: {},
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
					containerColor = MaterialTheme.colorScheme.inverseOnSurface
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
					contentDescription = stringResource(id = R.string.tic_tac_toe_image_content_desc),
					colorFilter = ColorFilter
						.tint(MaterialTheme.colorScheme.onPrimaryContainer),
					modifier = Modifier.padding(16.dp)
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
				value = state.name,
				textStyle = MaterialTheme.typography.bodyMedium,
				onValueChange = {
					onUserNameEvents(UserNameEvents.OnUserNameChange(it))
				},
				placeholder = {
					Text(text = stringResource(id = R.string.player_name_field_placeholder))
				},
				leadingIcon = {
					Icon(
						imageVector = Icons.Outlined.Person,
						contentDescription = stringResource(id = R.string.person_icon_content_desc)
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
				modifier = Modifier.padding(vertical = 8.dp),
				color = MaterialTheme.colorScheme.outline
			)
			Button(
				onClick = {
					when {
						state.name.isEmpty() -> {
							onUserNameEvents(UserNameEvents.ToggleUserNameDialog)
						}

						else -> onAnonymousPlay()
					}
				},
				modifier = Modifier
					.fillMaxWidth()
					.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
				elevation = ButtonDefaults
					.elevatedButtonElevation(defaultElevation = 2.dp),
				shape = MaterialTheme.shapes.medium,
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.primary,
					contentColor = MaterialTheme.colorScheme.onPrimary
				)
			) {
				Text(
					text = stringResource(id = R.string.on_boarding_play_now_text),
					style = MaterialTheme.typography.titleMedium
						.copy(fontFamily = KgShadowFontFamily)
				)
			}
			Spacer(modifier = Modifier.height(8.dp))
			Row(
				modifier = Modifier.fillMaxWidth()
			) {
				FilledTonalButton(
					onClick = {
						when {
							state.name.isEmpty() -> {
								onUserNameEvents(UserNameEvents.ToggleUserNameDialog)
							}

							else -> onCreateRedirect()
						}
					},
					modifier = Modifier
						.weight(.5f)
						.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
					shape = MaterialTheme.shapes.medium,
					colors = ButtonDefaults.filledTonalButtonColors(
						contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
						containerColor = MaterialTheme.colorScheme.primaryContainer
					)
				) {
					Text(
						text = stringResource(id = R.string.create_room_button_text),
						style = MaterialTheme.typography.bodyLarge
							.copy(fontFamily = KgShadowFontFamily)
					)
				}
				Spacer(modifier = Modifier.width(8.dp))
				FilledTonalButton(
					onClick = {
						when {
							state.name.isEmpty() -> {
								onUserNameEvents(UserNameEvents.ToggleUserNameDialog)
							}

							else -> onJoinRedirect()
						}
					},
					modifier = Modifier
						.weight(.5f)
						.sizeIn(minHeight = dimensionResource(id = R.dimen.button_height)),
					shape = MaterialTheme.shapes.medium,
					colors = ButtonDefaults.filledTonalButtonColors(
						contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
						containerColor = MaterialTheme.colorScheme.secondaryContainer
					)
				) {
					Text(
						text = stringResource(id = R.string.on_boarding_join_with_code_text),
						style = MaterialTheme.typography.bodyLarge
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
}


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun OnBoardingScreenPreview() {
	AndroidTicTacToeTheme {
		OnBoardingScreen(
			state = ChangeUserNameState(),
			onUserNameEvents = {},
			onJoinRedirect = {},
			onCreateRedirect = {},
			onAnonymousPlay = {},
		)
	}
}