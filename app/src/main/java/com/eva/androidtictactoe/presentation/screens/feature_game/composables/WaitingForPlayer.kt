package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.R
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme

@Composable
fun WaitingForPlayer(
	modifier: Modifier = Modifier,
	animationDuration: Int = 6000,
) {
	val infiniteTransition = rememberInfiniteTransition(label = "Watch glass transition")

	val rotate by infiniteTransition.animateFloat(
		initialValue = 0.0f,
		targetValue = 360.0f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = animationDuration,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Restart
		),
		label = "Actual rotate transition"
	)

	Column(
		modifier = modifier.padding(vertical = 4.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_waiting),
			contentDescription = "Waiting for other",
			colorFilter = ColorFilter
				.tint(MaterialTheme.colorScheme.onPrimaryContainer),
			modifier = Modifier
				.graphicsLayer {
					rotationZ = rotate
				}
				.sizeIn(maxHeight = 100.dp, maxWidth = 100.dp)
		)
		Spacer(modifier = Modifier.height(16.dp))
		Text(
			text = stringResource(id = R.string.waiting),
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onSurface,
			textAlign = TextAlign.Center,
		)
		Spacer(modifier = Modifier.height(2.dp))
		Text(
			text = stringResource(id = R.string.waiting_extra),
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			textAlign = TextAlign.Center,
		)
	}

}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun WaitingForOtherPlayerPreview() {
	AndroidTicTacToeTheme {
		Surface {
			WaitingForPlayer()
		}
	}
}