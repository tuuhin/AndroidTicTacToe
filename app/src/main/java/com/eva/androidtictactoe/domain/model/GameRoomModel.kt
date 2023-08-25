package com.eva.androidtictactoe.domain.model

data class GameRoomModel(
    val room: String,
    val players: List<GamePlayerModel> = emptyList(),
    val board: BoardGameModel = BoardGameModel(),
    val boardCount: Int = 1,
    val isGameComplete: Boolean = false
)
