package com.eva.androidtictactoe.domain.model

typealias  TicTacToeBoardFace = List<List<GameSymbols>>

enum class GameSymbols(val symbol: Char) {
	XSymbol(symbol = 'X'),
	OSymbol(symbol = 'O'),
	Blank(symbol = 'B');

	companion object {
		fun fromSymbol(symbol: Char): GameSymbols = when (symbol) {
			'X' -> XSymbol
			'Y' -> OSymbol
			else -> Blank
		}
	}
}
