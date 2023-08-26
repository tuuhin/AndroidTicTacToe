package com.eva.androidtictactoe.presentation.screens.feature_game

import androidx.lifecycle.ViewModel
import com.eva.androidtictactoe.domain.model.BoardGameModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameScreenViewModel : ViewModel() {

    private val _boardState = MutableStateFlow(BoardGameModel())
    val boardState = _boardState.asStateFlow()


}