package com.eva.androidtictactoe.presentation.screens.feature_game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.androidtictactoe.domain.model.BoardSymbols
import com.eva.androidtictactoe.presentation.screens.feature_game.composables.TicTacToeBoard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    playerSymbols: BoardSymbols,
    navigation: (@Composable () -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Game") },
                navigationIcon = navigation ?: {}
            )
        }
    ) { scPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(scPadding)
                .padding(horizontal = 20.dp)
        ) {
            TicTacToeBoard(
                onTap = {},
                playerSymbols = playerSymbols,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(playerSymbols = BoardSymbols.XSymbol)
}