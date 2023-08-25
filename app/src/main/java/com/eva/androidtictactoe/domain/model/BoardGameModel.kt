package com.eva.androidtictactoe.domain.model

data class BoardGameModel(
    val boardState: List<List<BoardSymbols>> = emptyBoardState(),
    val isDraw: Boolean = false
) {
    companion object {
        fun emptyBoardState(): List<List<BoardSymbols>> {
            return listOf(
                listOf(BoardSymbols.Blank, BoardSymbols.Blank, BoardSymbols.Blank),
                listOf(BoardSymbols.Blank, BoardSymbols.Blank, BoardSymbols.Blank),
                listOf(BoardSymbols.Blank, BoardSymbols.Blank, BoardSymbols.Blank)
            )
        }
    }
}
