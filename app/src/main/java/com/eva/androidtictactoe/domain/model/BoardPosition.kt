package com.eva.androidtictactoe.domain.model

data class BoardPosition(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZeroZero = BoardPosition(0, 0)
    }
}
