package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.domain.model.GamePlayerModel
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.IsoMetricFontFamily

@Composable
fun GamePlayerProfile(
	mode: String,
	modifier: Modifier = Modifier,
	player: GamePlayerModel,
	containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
	symbolColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
	OutlinedCard(
		modifier = modifier.padding(4.dp),
		shape = MaterialTheme.shapes.medium,
		border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
	) {
		Text(
			text = mode,
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.padding(4.dp)
		)
		Box(
			modifier = Modifier
				.clip(MaterialTheme.shapes.extraSmall)
				.background(containerColor)
				.align(Alignment.CenterHorizontally)
		) {
			Text(
				text = "${player.symbol.symbol}",
				style = MaterialTheme.typography.displayMedium
					.copy(fontFamily = IsoMetricFontFamily),
				color = symbolColor,
				modifier = Modifier.padding(4.dp)
			)
		}
		Text(
			text = player.userName,
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.padding(4.dp)
		)

		Row(
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
		) {
			Text(
				text = "Win : ${player.winCount}",
				color = symbolColor,
				style = MaterialTheme.typography.labelLarge
			)

			Text(
				text = "Draw : ${player.drawCount}",
				color = MaterialTheme.colorScheme.outline,
				style = MaterialTheme.typography.labelLarge
			)
		}
		Text(
			text = "Loose : ${player.looseCount}",
			color = MaterialTheme.colorScheme.error,
			style = MaterialTheme.typography.labelLarge,
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.padding(2.dp)
		)
	}

}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GamePlayerProfilePreview() {
	AndroidTicTacToeTheme {
		Surface(color = MaterialTheme.colorScheme.background) {
			GamePlayerProfile(
				mode = "Opponent",
				player = FakePreview.FAKE_GAME_PLAYER_MODEL,
				modifier = Modifier.wrapContentWidth()
			)
		}
	}
}