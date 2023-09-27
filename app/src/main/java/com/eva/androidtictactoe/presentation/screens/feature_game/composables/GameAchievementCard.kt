package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.GameAchievementModel
import com.eva.androidtictactoe.domain.model.GameSymbols
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.IsoMetricFontFamily

@Composable
fun GameAchievementCard(
	model: GameAchievementModel,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.clip(MaterialTheme.shapes.large)
			.background(MaterialTheme.colorScheme.secondaryContainer),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		model.winnerSymbols?.let { symbol ->
			Box(
				modifier = Modifier
					.padding(8.dp)
					.clip(MaterialTheme.shapes.medium)
					.background(MaterialTheme.colorScheme.tertiaryContainer)
			) {
				Text(
					text = "$symbol",
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
		} ?: kotlin.run {
			Box(
				modifier = Modifier
					.padding(8.dp)
					.clip(MaterialTheme.shapes.medium)
					.background(MaterialTheme.colorScheme.tertiaryContainer)
			) {
				Text(
					text = "${GameSymbols.XSymbol.symbol}",
					style = MaterialTheme.typography.displayMedium
						.copy(fontFamily = IsoMetricFontFamily),
					color = MaterialTheme.colorScheme.onTertiaryContainer,
					modifier = Modifier.padding(4.dp)
				)
			}
			Text(
				text = "Draw",
				style = MaterialTheme.typography.titleMedium,
				modifier = Modifier.weight(1f),
				color = MaterialTheme.colorScheme.onSecondaryContainer,
				textAlign = TextAlign.Center,
			)
			Box(
				modifier = Modifier
					.padding(8.dp)
					.clip(MaterialTheme.shapes.medium)
					.background(MaterialTheme.colorScheme.tertiaryContainer)
			) {
				Text(
					text = "${GameSymbols.OSymbol.symbol}",
					style = MaterialTheme.typography.displayMedium
						.copy(fontFamily = IsoMetricFontFamily),
					color = MaterialTheme.colorScheme.onTertiaryContainer,
					modifier = Modifier.padding(4.dp)
				)
			}
		}
	}
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun GameAchievementCardPreview(
	@PreviewParameter(GameAchievementPreviewParams::class)
	model: GameAchievementModel
) = AndroidTicTacToeTheme {
	GameAchievementCard(
		model = model,
		modifier = Modifier.fillMaxWidth()
	)
}
