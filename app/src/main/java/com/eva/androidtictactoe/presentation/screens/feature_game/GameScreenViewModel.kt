package com.eva.androidtictactoe.presentation.screens.feature_game

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.androidtictactoe.data.local.UserPreferencesFacade
import com.eva.androidtictactoe.domain.model.BoardGameModel
import com.eva.androidtictactoe.domain.model.BoardPosition
import com.eva.androidtictactoe.domain.repository.GameRepository
import com.eva.androidtictactoe.presentation.navigation.ScreenParameters.ROOM_CODE_PARAMS
import com.eva.androidtictactoe.presentation.screens.feature_game.util.GameScreenEvents
import com.eva.androidtictactoe.presentation.utils.UiEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameScreenViewModel(
	private val gameRepo: GameRepository,
	preferencesFacade: UserPreferencesFacade,
	savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val gameTag = "GAME_VIEW_MODEL"

	val serverMessages: StateFlow<String>
		get() = gameRepo.serverMessage

	val gameEvents = gameRepo.gameBoard
		.stateIn(
			viewModelScope,
			SharingStarted.WhileSubscribed(2000L),
			BoardGameModel.UNINITIALIZED_GAME_ROOM
		)

	val connectionEventsError =
		gameRepo.connectionEvents.map { UiEvents.ShowSnackBar(message = it) }
			.shareIn(viewModelScope, SharingStarted.Lazily)

	init {
		savedStateHandle.getStateFlow<String?>(ROOM_CODE_PARAMS, null)
			.onEach { roomCode ->
				val playerName = preferencesFacade.getPlayerName()
				roomCode?.let { code ->
					gameRepo.connectWithRoomId(room = code, userName = playerName)
				} ?: gameRepo.connectAnonymously(userName = playerName)

			}
			.launchIn(viewModelScope)
	}


	private val _allowBack = MutableStateFlow(false)
	val allowBack = _allowBack.asStateFlow()

	fun onEvent(event: GameScreenEvents) {
		when (event) {
			GameScreenEvents.BackButtonPressed -> {}
			is GameScreenEvents.OnBoardPositionSelect -> onPositionSelect(event.position)
		}

	}

	private fun onPositionSelect(position: BoardPosition) = viewModelScope.launch {
		gameRepo.sendBoardData(position)
	}

	override fun onCleared() {
		viewModelScope.launch {
			gameRepo.onDisConnect()
			Log.d(gameTag, "DISCONNECT RESULT")
		}
		super.onCleared()
	}
}