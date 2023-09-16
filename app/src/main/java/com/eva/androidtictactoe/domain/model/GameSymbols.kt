package com.eva.androidtictactoe.domain.model

typealias  TicTacToeBoardFace = List<List<GameSymbols>>

enum class GameSymbols(val symbol: Char) {
	XSymbol(symbol = 'X'),
	OSymbol(symbol = 'O'),
	Blank(symbol = 'B');

	companion object {
		fun fromSymbol(symbol: Char): GameSymbols = when (symbol) {
			XSymbol.symbol -> XSymbol
			OSymbol.symbol -> OSymbol
			else -> Blank
		}
	}

	override fun toString(): String {
		return this.symbol.toString()
	}
}
