package com.eva.androidtictactoe.presentation.screens.feature_room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.androidtictactoe.data.local.UserPreferencesFacade
import com.eva.androidtictactoe.domain.repository.PlayerRoomRepository
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.CheckRoomState
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.CreateRoomState
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.RoomEvents
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.UserNameEvents
import com.eva.androidtictactoe.presentation.utils.UiEvents
import com.eva.androidtictactoe.utils.ApiPaths
import com.eva.androidtictactoe.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerRoomViewModel(
	private val repo: PlayerRoomRepository,
	private val preferences: UserPreferencesFacade,
) : ViewModel() {

	private val _localName = preferences.playerUserName

	private val _uiEvents = MutableSharedFlow<UiEvents>()
	val uiEvents = _uiEvents.asSharedFlow()

	private val _createRoomState = MutableStateFlow(CreateRoomState())
	val createRoomState = _createRoomState.asStateFlow()

	private val _checkRoomState = MutableStateFlow(CheckRoomState())
	val checkRoomState = _checkRoomState.asStateFlow()

	private val _userName = MutableStateFlow("")
	val userName = merge(_userName, _localName)
		.stateIn(
			viewModelScope,
			SharingStarted.Eagerly,
			initialValue = ""
		)

	fun onUserNameEvents(event: UserNameEvents) {
		when (event) {
			is UserNameEvents.OnUserNameChange -> _userName.update { event.value }
			UserNameEvents.OnUserNameChangeSave -> saveUserName()
			else -> {}
		}
	}


	private fun saveUserName() = viewModelScope.launch {
		preferences.setPlayerUserName(userName.value)
	}

	fun onCheckRoomEvents(event: RoomEvents) {
		when (event) {
			is RoomEvents.OnValueChange -> _checkRoomState.update {
				it.copy(roomId = event.count)
			}

			RoomEvents.RoomDataRequest -> onJoinRoom(_checkRoomState.value.roomId)

			RoomEvents.OnDialogToggle -> _checkRoomState.update {
				it.copy(showDialog = !_checkRoomState.value.showDialog)
			}

			is RoomEvents.ToggleLoadingState -> _checkRoomState.update {
				it.copy(isLoading = event.state)
			}

			is RoomEvents.OnDialogConfirm -> {}
		}
	}

	fun onCreateRoomEvents(event: RoomEvents) {
		when (event) {
			is RoomEvents.OnValueChange -> _createRoomState.update {
				it.copy(boardCount = event.count)
			}

			RoomEvents.RoomDataRequest -> _createRoomState.value.boardCount.toIntOrNull()
				?.let { board -> onCreateRoom(board) } ?: onCreateRoom()

			RoomEvents.OnDialogToggle -> _createRoomState.update {
				it.copy(showDialog = !_createRoomState.value.showDialog)
			}

			is RoomEvents.ToggleLoadingState -> _createRoomState.update {
				it.copy(isLoading = event.state)
			}

			is RoomEvents.OnDialogConfirm -> {
				_createRoomState.update { it.copy(showDialog = false) }
				_checkRoomState.update { it.copy(roomId = event.roomId) }
				viewModelScope.launch {
					_uiEvents.emit(UiEvents.Navigate(route = ApiPaths.CHECK_ROOM_PATH))
				}
			}
		}
	}

	private fun onCreateRoom(board: Int = 1) {
		viewModelScope.launch(Dispatchers.IO) {
			repo.createRoom(board).onEach { res ->
				Log.d("RES", res.toString())
				when (res) {
					is Resource.Error -> {
						_createRoomState.update { it.copy(isLoading = false) }
						_uiEvents.emit(UiEvents.ShowSnackBar(message = res.message))
					}

					is Resource.Loading -> _createRoomState.update { it.copy(isLoading = true) }
					is Resource.Success -> {
						_createRoomState.update { state ->
							state.copy(isLoading = false, showDialog = true, response = res.data)
						}
					}
				}
			}.launchIn(this)
		}
	}

	private fun onJoinRoom(roomId: String) {
		viewModelScope.launch(Dispatchers.IO) {
			repo.joinRoom(roomId).onEach { res ->
				Log.d("RES", res.toString())
				when (res) {
					is Resource.Error -> {
						_checkRoomState.update { it.copy(isLoading = false) }
						_uiEvents.emit(UiEvents.ShowSnackBar(message = res.message))
					}

					is Resource.Loading -> _checkRoomState.update { it.copy(isLoading = true) }
					is Resource.Success -> {
						_checkRoomState.update { state ->
							state.copy(
								isLoading = false,
								showDialog = true,
								response = res.data.roomModel
							)
						}
					}
				}
			}.launchIn(this)
		}
	}
}