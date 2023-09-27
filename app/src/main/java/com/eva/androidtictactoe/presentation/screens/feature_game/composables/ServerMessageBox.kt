package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme

@Composable
fun ServerMessageBox(
	messages: String,
	modifier: Modifier = Modifier,
	shape: CornerBasedShape = MaterialTheme.shapes.small,
) {
	Card(
		modifier = modifier,
		shape = shape,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 4.dp,
			pressedElevation = 6.dp,
		),
	) {
		Column(
			modifier = Modifier
				.padding(all = dimensionResource(id = R.dimen.scaffold_horizontal_padding))
		) {

			Text(
				text = stringResource(id = R.string.message_box_heading),
				style = MaterialTheme.typography.titleMedium,
			)
			Divider(
				modifier = Modifier.padding(vertical = 2.dp),
				color = MaterialTheme.colorScheme.onSecondaryContainer
			)
			Text(
				text = messages,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis,
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.padding(vertical = 4.dp)
			)
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
fun ServerMessageBoxPreview() = AndroidTicTacToeTheme {
	ServerMessageBox(
		messages = "This is secret message",
		modifier = Modifier.fillMaxWidth(),
	)
}
