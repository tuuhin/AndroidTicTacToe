package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eva.androidtictactoe.domain.model.BoardPosition
import com.eva.androidtictactoe.domain.model.GameSymbols
import com.eva.androidtictactoe.domain.model.TicTacToeBoardFace
import com.eva.androidtictactoe.domain.model.TicTacToeBoardModel
import com.eva.androidtictactoe.presentation.screens.feature_game.util.asPretty
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.AndroidTicTacToeTheme
import com.eva.androidtictactoe.ui.theme.OutlineFontFamily

@Composable
fun TicTacToeBoard(
	modifier: Modifier = Modifier,
	onTap: (BoardPosition) -> Unit,
	playerSymbols: GameSymbols,
	board: TicTacToeBoardFace = TicTacToeBoardModel.emptyBoardState(),
	boardColor: Color = MaterialTheme.colorScheme.surface,
	frameColor: Color = MaterialTheme.colorScheme.outline,
	playerColor: Color = MaterialTheme.colorScheme.primaryContainer,
	onPlayerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	opponentColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
	onOpponentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer
) {
	check(
		value = playerSymbols != GameSymbols.Blank,
		lazyMessage = { "Blank type is only for the board,Player cannot use the blank type" })

	val textMeasurer = rememberTextMeasurer()

	SideEffect {
		Log.d("BOARD", board.asPretty())
	}

	Box(
		modifier = modifier
			.aspectRatio(1f)
			.clip(MaterialTheme.shapes.large)
			.background(color = boardColor, shape = MaterialTheme.shapes.large)
			.border(2.dp, frameColor, MaterialTheme.shapes.large)
	) {
		Canvas(
			modifier = Modifier
				.fillMaxSize()
				.pointerInput(Unit) {
					detectTapGestures(
						onTap = { point ->
							var position = BoardPosition.ZeroZero
							val boxWidth = size.width / 3
							val boxHeight = size.height / 3
							position = when {
								point.x < boxWidth -> position.copy(x = 0)
								point.x > boxWidth && point.x < boxWidth * 2 -> position.copy(x = 1)
								else -> position.copy(x = 2)
							}
							position = when {
								point.y < boxHeight -> position.copy(y = 0)
								point.y > boxWidth && point.y < boxHeight * 2 -> position.copy(y = 1)
								else -> position.copy(y = 2)
							}
							onTap(position)
						}
					)
				}
		) {
			val boxWidth = size.width / 3
			val boxHeight = size.height / 3
			val boxSize = Size(boxWidth, boxHeight)
			// drawing squares
			board.forEachIndexed { row, boardRow ->
				boardRow.forEachIndexed { col, item ->
					val start = Offset(boxWidth * row, boxHeight * col)

					val containerColor = when (item) {
						GameSymbols.XSymbol -> if (playerSymbols == item) playerColor else opponentColor
						GameSymbols.OSymbol -> if (playerSymbols == item) playerColor else opponentColor
						GameSymbols.Blank -> Color.Transparent
					}

					val textColor = when (item) {
						GameSymbols.XSymbol -> if (playerSymbols == item) onPlayerColor else onOpponentColor
						GameSymbols.OSymbol -> if (playerSymbols == item) onPlayerColor else onOpponentColor
						else -> Color.Transparent
					}

					drawRect(
						color = containerColor,
						topLeft = start,
						size = boxSize,
						alpha = 0.75f
					)

					if (item != GameSymbols.Blank) {
						val fontSize = 100.sp
						val centerOffset = Offset(boxWidth / 2, boxHeight / 2)

						val textLayoutResult = textMeasurer.measure(
							text = AnnotatedString("${item.symbol}"), style = TextStyle(
								fontSize = fontSize,
								fontWeight = FontWeight.Bold,
								fontFamily = OutlineFontFamily
							)
						)
						val textSize = textLayoutResult.size

						val fontOffset = Offset(textSize.width / 2f, textSize.height / 2f)

						val textPos = start + centerOffset - fontOffset

						drawText(
							textMeasurer = textMeasurer,
							text = "${item.symbol}",
							topLeft = textPos,
							style = TextStyle(
								fontSize = fontSize,
								fontWeight = FontWeight.Bold,
								fontFamily = OutlineFontFamily,
								color = textColor
							)
						)
					}
				}
			}

			val frameWidth = 2.dp.toPx()

			repeat(2) { idx ->
				// Draw lines over x axis
				drawLine(
					color = frameColor,
					start = Offset(size.width / 3 * (idx + 1), 0f),
					end = Offset(size.width / 3 * (idx + 1), size.height),
					strokeWidth = frameWidth,
					cap = StrokeCap.Round
				)
				// Draw lines over y axis
				drawLine(
					color = frameColor,
					start = Offset(0f, size.height / 3 * (idx + 1)),
					end = Offset(size.height, size.height / 3 * (idx + 1)),
					strokeWidth = frameWidth,
					cap = StrokeCap.Round
				)
			}

		}
	}
}

class BoardPreviewParameter : CollectionPreviewParameterProvider<TicTacToeBoardFace>(
	listOf(
		FakePreview.DIAGONAL_BOARD_FILLED_BY_X,
		FakePreview.DIAGONAL_FILLED_BOARD_BY_O,
		FakePreview.BOARD_RANDOM_FILLED_BY_X_AND_O
	)
)

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun TicTacToeBoardPreview(
	@PreviewParameter(BoardPreviewParameter::class)
	board: List<List<GameSymbols>>
) {
	AndroidTicTacToeTheme {
		Surface(color = MaterialTheme.colorScheme.background) {
			TicTacToeBoard(
				playerSymbols = GameSymbols.XSymbol,
				board = board,
				modifier = Modifier.padding(12.dp),
				onTap = {}
			)
		}
	}
}