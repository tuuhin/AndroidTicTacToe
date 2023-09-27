package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.GameAchievementModel
import com.eva.androidtictactoe.presentation.screens.feature_game.GameAchievementEvents
import com.eva.androidtictactoe.presentation.screens.feature_game.GameAchievementState
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
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
				GameAchievementCard(
					model = model,
					modifier = Modifier.fillMaxWidth(),
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = model.text,
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onPrimaryContainer,
					modifier = Modifier.align(Alignment.CenterHorizontally),
					textAlign = TextAlign.Center,
				)
				model.secondaryText?.let { text ->
					Text(
						text = text,
						style = MaterialTheme.typography.labelSmall,
						color = MaterialTheme.colorScheme.onSecondaryContainer,
						modifier = Modifier.align(Alignment.CenterHorizontally),
						textAlign = TextAlign.Center,
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

class GameAchievementPreviewParams : CollectionPreviewParameterProvider<GameAchievementModel>(
	listOf(
		FakePreview.FAKE_ACHIEVEMENT_MODEL_WITH_WINNER,
		FakePreview.FAKE_ACHIEVEMENT_MODEL_WITH_DRAW
	)
)


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun FinishedGameDialogPreview(

	@PreviewParameter(GameAchievementPreviewParams::class)
	model: GameAchievementModel,

	) = AndroidTicTacToeTheme {
	FinishGameDialog(
		model = model,
		onConfirm = {},
	)
}
