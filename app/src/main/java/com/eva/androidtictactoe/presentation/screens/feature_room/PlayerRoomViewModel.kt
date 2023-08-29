package com.eva.androidtictactoe.presentation.screens.feature_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.androidtictactoe.data.local.UserPreferencesFacade
import com.eva.androidtictactoe.domain.repository.PlayerRoomRepository
import com.eva.androidtictactoe.presentation.utils.UiEvents
import com.eva.androidtictactoe.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerRoomViewModel(
	private val repo: PlayerRoomRepository,
	private val preferences: UserPreferencesFacade,
) : ViewModel() {

	private val _localName = preferences.playerUserNameAsFlow

	private val _uiEvents = MutableSharedFlow<UiEvents>()
	val uiEvents = _uiEvents.asSharedFlow()

	private val _boardCount = MutableStateFlow(1)
	val boardCount = _boardCount.asStateFlow()

	private val _roomChecker = MutableStateFlow("")
	val roomId = _roomChecker.asStateFlow()

	private val _userName = MutableStateFlow("")

	val userName = combine(_userName, _localName) { userName, local ->
		userName.ifEmpty { local }
	}.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		initialValue = ""
	)

	fun onBoardCountChange(value: String) =
		value.toIntOrNull()
			?.let { board -> _boardCount.update { board } }
			?: _boardCount.update { 1 }

	fun onRoomIdChange(room: String) = _roomChecker.update { room }

	fun onUserNameChange(userName: String) = _userName.update { userName }

	fun onUserNameChangeDone() = viewModelScope.launch {
		preferences.setPlayerUserName(userName.value)
	}

	private fun onCreateRoom(board: Int) {
		viewModelScope.launch(Dispatchers.IO) {
			repo.createRoom(board).onEach { res ->
				when (res) {
					is Resource.Error -> _uiEvents.emit(UiEvents.ShowSnackBar(message = res.message))
					is Resource.Loading -> {}
					is Resource.Success -> _uiEvents.emit(UiEvents.ShowSnackBar(message = res.data.roomId))
				}
			}.launchIn(this)
		}
	}

	private fun onJoinRoom(roomId: String) {
		viewModelScope.launch(Dispatchers.IO) {
			repo.joinRoom(roomId).onEach { res ->
				when (res) {
					is Resource.Error -> _uiEvents.emit(UiEvents.ShowSnackBar(message = res.message))
					is Resource.Loading -> {}
					is Resource.Success -> {}
				}
			}.launchIn(this)
		}
	}
}