package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.domain.model.GamePlayerModel
import com.eva.androidtictactoe.domain.model.GameSymbols
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme

@Composable
fun GameInfoHeader(
	player: GamePlayerModel,
	opponent: GamePlayerModel,
	roundNumber: Int,
	boardCount: Int,
	modifier: Modifier = Modifier,
) {
	val roundRatio by remember(roundNumber) {
		derivedStateOf {
			if (boardCount != 0)
				"$roundNumber/$boardCount"
			else
				""
		}
	}

	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.SpaceAround,
		verticalAlignment = Alignment.CenterVertically,
	) {
		GamePlayerProfile(
			mode = stringResource(id = R.string.player_profile_text),
			player = player,
			modifier = Modifier
				.padding(vertical = 2.dp),
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			symbolColor = MaterialTheme.colorScheme.onPrimaryContainer,
		)
		Card(
			shape = MaterialTheme.shapes.large,
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.tertiaryContainer,
				contentColor = MaterialTheme.colorScheme.onTertiaryContainer
			),
			elevation = CardDefaults
				.elevatedCardElevation(defaultElevation = 2.dp),
		) {
			Column(
				modifier = Modifier.padding(16.dp)
			) {
				Text(
					text = stringResource(id = R.string.game_round_heading),
					style = MaterialTheme.typography.titleMedium,
					modifier = Modifier
						.align(Alignment.CenterHorizontally),
				)
				Text(
					text = roundRatio,
					style = MaterialTheme.typography.bodyMedium,
					modifier = Modifier
						.align(Alignment.CenterHorizontally),
				)
			}
		}
		GamePlayerProfile(
			mode = stringResource(id = R.string.opponent_profile_text),
			player = opponent,
			modifier = Modifier.padding(vertical = 2.dp),
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			symbolColor = MaterialTheme.colorScheme.onSecondaryContainer
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
fun GameInfoHeaderPreview() {
	AndroidTicTacToeTheme {
		Surface {
			GameInfoHeader(
				player = FakePreview.FAKE_GAME_PLAYER_MODEL,
				opponent = FakePreview.FAKE_GAME_PLAYER_MODEL.copy(symbol = GameSymbols.XSymbol),
				boardCount = 3,
				roundNumber = 2,
				modifier = Modifier.fillMaxWidth(),
			)
		}
	}
}