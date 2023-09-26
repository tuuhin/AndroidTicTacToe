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
import com.eva.androidtictactoe.presentation.utils.UiEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameScreenViewModel(
	private val gameRepository: GameRepository,
	preferences: UserPreferencesFacade,
	savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val gameTag = "GAME_VIEW_MODEL"

	val serverMessages: StateFlow<String>
		get() = gameRepository.serverMessage

	val gameEvents = gameRepository.gameBoard.stateIn(
		viewModelScope,
		SharingStarted.WhileSubscribed(2000L),
		BoardGameModel.UNINITIALIZED_GAME_ROOM
	)

	val connectionEventsError: SharedFlow<UiEvents> =
		gameRepository.connectionEvents.map { UiEvents.ShowSnackBar(message = it) }
			.shareIn(viewModelScope, SharingStarted.Lazily)


	private val _achievementState = MutableStateFlow(GameAchievementState())
	val achievements = _achievementState.asStateFlow()


	private val _backHandlerState = MutableStateFlow(GameBackHandlerState())
	val backHandlerState = _backHandlerState.asStateFlow()

	init {

		viewModelScope.launch {

			val playerName = preferences.getPlayerName()

			savedStateHandle.getStateFlow<String?>(key = ROOM_CODE_PARAMS, initialValue = null)
				.onEach { roomCode ->

					roomCode?.let { code ->
						gameRepository.connectWithRoomId(room = code, userName = playerName)
					} ?: gameRepository.connectAnonymously(userName = playerName)

				}.launchIn(this)
		}

		gameRepository.gameAchievements.onEach { achievement ->
			_achievementState.update { state ->
				state.copy(
					showAchievement = true, achievement = achievement
				)
			}
		}.launchIn(viewModelScope)

	}

	fun onAchievementEvents(event: GameAchievementEvents) {
		when (event) {
			GameAchievementEvents.AcceptQuitGameDialog -> {}
		}
	}

	fun onBackHandlerEvents(event: GameBackHandlerEvents) {
		when (event) {
			GameBackHandlerEvents.OnBackButtonPressed -> _backHandlerState.update { state ->
				state.copy(showOnBackDialog = true)
			}

			GameBackHandlerEvents.CancelGame -> {}

			GameBackHandlerEvents.ToggleCancelGameDialog -> _backHandlerState.update { state ->
				state.copy(showOnBackDialog = !state.showOnBackDialog)
			}
		}
	}

	fun onPositionSelect(position: BoardPosition) = viewModelScope.launch {
		gameRepository.sendBoardData(position)
	}

	override fun onCleared() {
		viewModelScope.launch {
			gameRepository.onDisConnect()
			Log.d(gameTag, "DISCONNECT RESULT")
		}
		super.onCleared()
	}
}