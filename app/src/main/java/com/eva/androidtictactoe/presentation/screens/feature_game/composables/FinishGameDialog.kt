package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.eva.androidtictactoe.domain.model.GameAchievementModel
import com.eva.androidtictactoe.presentation.screens.feature_game.GameAchievementEvents
import com.eva.androidtictactoe.presentation.screens.feature_game.GameAchievementState
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.IsoMetricFontFamily
import com.eva.androidtictactoe.ui.theme.KgShadowFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishGameDialog(
	model: GameAchievementModel,
	onConfirm: () -> Unit,
	modifier: Modifier = Modifier,
) {
	AlertDialog(
		onDismissRequest = {},
		modifier = modifier
	) {
		Surface(
			shape = MaterialTheme.shapes.large,
			tonalElevation = 8.dp
		) {
			Column(
				verticalArrangement = Arrangement.spacedBy(4.dp),
				modifier = Modifier
					.fillMaxWidth()
					.background(MaterialTheme.colorScheme.inverseOnSurface)
					.padding(16.dp)
			) {
				Text(
					text = stringResource(id = R.string.achievement_dialog_header),
					style = MaterialTheme.typography.headlineSmall,
					color = MaterialTheme.colorScheme.onPrimaryContainer
				)
				Spacer(modifier = Modifier.height(8.dp))
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentHeight()
						.clip(MaterialTheme.shapes.large)
						.background(MaterialTheme.colorScheme.secondaryContainer),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Box(
						modifier = Modifier
							.padding(8.dp)
							.clip(MaterialTheme.shapes.medium)
							.background(MaterialTheme.colorScheme.tertiaryContainer)
					) {
						Text(
							text = "${model.winnerSymbols.symbol}",
							style = MaterialTheme.typography.displayMedium
								.copy(fontFamily = IsoMetricFontFamily),
							color = MaterialTheme.colorScheme.onTertiaryContainer,
							modifier = Modifier.padding(4.dp)
						)
					}
					model.winnerName?.let { name ->
						Column {
							Text(
								text = stringResource(id = R.string.winner),
								style = MaterialTheme.typography.bodyLarge,
								color = MaterialTheme.colorScheme.onSecondaryContainer
							)
							Text(
								text = name,
								style = MaterialTheme.typography.titleMedium,
								color = MaterialTheme.colorScheme.onSurface
							)
						}
					}
				}
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = model.text,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.secondary
				)
				model.secondaryText?.let { text ->
					Text(
						text = text,
						style = MaterialTheme.typography.labelSmall,
						color = MaterialTheme.colorScheme.onSecondaryContainer
					)
				}
				Spacer(modifier = Modifier.height(4.dp))
				TextButton(
					onClick = onConfirm,
					modifier = Modifier.align(Alignment.End),
					colors = ButtonDefaults
						.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
				) {
					Text(
						text = stringResource(id = R.string.achievement_dialog_buttton_text),
						style = MaterialTheme.typography.titleMedium
							.copy(fontFamily = KgShadowFontFamily)
					)
				}
			}
		}
	}
}

@Composable
fun FinishGameDialog(
	state: GameAchievementState,
	onEvents: (GameAchievementEvents) -> Unit,
	modifier: Modifier = Modifier,
) {
	state.achievement?.let { model ->
		FinishGameDialog(
			model = model,
			onConfirm = { onEvents(GameAchievementEvents.AcceptQuitGameDialog) },
			modifier = modifier,
		)
	}
}


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun FinishedGameDialogPreview() {
	AndroidTicTacToeTheme {
		FinishGameDialog(
			model = FakePreview.FAKE_ACHIEVEMENT_MODEL,
			onConfirm = {}
		)
	}
}