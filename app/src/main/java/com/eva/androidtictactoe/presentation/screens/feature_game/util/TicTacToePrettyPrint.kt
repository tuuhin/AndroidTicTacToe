package com.eva.androidtictactoe.presentation.screens.feature_game.util

import com.eva.androidtictactoe.domain.model.TicTacToeBoardFace

fun TicTacToeBoardFace.asPretty(): String {
	val builder = buildString {
		this@asPretty.forEach { boardRow ->
			boardRow.forEach { item ->
				append(" | ${item.symbol}")
			}
			append("|")
			append("\n--------------\n")
		}
	}
	return builder
}