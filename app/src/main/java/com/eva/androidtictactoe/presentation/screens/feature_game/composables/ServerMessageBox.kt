package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme

@Composable
fun ServerMessageBox(
	modifier: Modifier = Modifier,
	messages: String
) {
	Card(
		modifier = modifier,
		shape = MaterialTheme.shapes.extraSmall,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			contentColor = MaterialTheme.colorScheme.onSecondaryContainer
		)
	) {
		Text(
			text = "Messages",
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier.padding(horizontal = 4.dp)
		)
		Divider(modifier = Modifier.padding(vertical = 2.dp))
		Text(
			text = messages,
			style = MaterialTheme.typography.bodyMedium,
			modifier = Modifier.padding(horizontal = 4.dp)
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
fun ServerMessageBoxPreview() {
	AndroidTicTacToeTheme {
		ServerMessageBox(
			messages = "THis is secret message",
			modifier = Modifier.fillMaxWidth(),
		)
	}
}