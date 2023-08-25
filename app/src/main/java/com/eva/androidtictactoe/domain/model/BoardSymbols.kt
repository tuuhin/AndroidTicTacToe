package com.eva.androidtictactoe.domain.model

enum class BoardSymbols(val symbol: Char) {
    XSymbol(symbol = 'X'),
    OSymbol(symbol = 'O'),
    Blank(symbol = 'B');

    companion object {
        fun fromSymbol(symbol: Char): BoardSymbols = when (symbol) {
            'X' -> XSymbol
            'Y' -> OSymbol
            else -> Blank
        }
    }
}
