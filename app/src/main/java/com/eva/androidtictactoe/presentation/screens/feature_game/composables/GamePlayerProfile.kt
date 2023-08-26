package com.eva.androidtictactoe.presentation.screens.feature_game.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eva.androidtictactoe.domain.model.GamePlayerModel
import com.eva.androidtictactoe.presentation.utils.FakePreview
import com.eva.androidtictactoe.ui.theme.OutlineFontFamily

@Composable
fun GamePlayerProfile(
    modifier: Modifier = Modifier,
    player: GamePlayerModel,
    border: BorderStroke? = null,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    symbolContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    symbolColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Card(
        modifier = modifier,
        border = border,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults
            .cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Room PLayer",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = player.userName,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, symbolColor, MaterialTheme.shapes.medium)
                        .background(symbolContainerColor)
                ) {
                    Text(
                        text = "${player.playerSymbol.symbol}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = symbolColor,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutlineFontFamily,
                            fontSize = 50.sp
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Win : ${player.winCount}",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Loose : ${player.winCount}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Draw : ${player.winCount}",
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GamePlayerProfilePreview() {
    GamePlayerProfile(
        player = FakePreview.FAKE_GAME_PLAYER_MODEL,
        modifier = Modifier.wrapContentWidth()
    )
}