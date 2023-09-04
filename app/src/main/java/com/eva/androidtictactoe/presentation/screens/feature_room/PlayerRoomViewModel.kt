package com.eva.androidtictactoe.presentation.screens.feature_room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.androidtictactoe.data.local.UserPreferencesFacade
import com.eva.androidtictactoe.domain.repository.PlayerRoomRepository
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.ChangeUserNameState
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.CreateRoomState
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.RoomInteractionEvents
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.UserNameEvents
import com.eva.androidtictactoe.presentation.screens.feature_room.utils.VerifyRoomState
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerRoomViewModel(
	private val playerRepo: PlayerRoomRepository,
	private val preferences: UserPreferencesFacade,
) : ViewModel() {

	private val _localName = preferences.playerUserName

	private val _uiEvents = MutableSharedFlow<UiEvents>()
	val uiEvents = _uiEvents.asSharedFlow()

	private val _createRoomState = MutableStateFlow(CreateRoomState())
	val createRoomState = _createRoomState.asStateFlow()

	private val _checkRoomState = MutableStateFlow(VerifyRoomState())
	val checkRoomState = _checkRoomState.asStateFlow()

	private val _userNameState = MutableStateFlow(ChangeUserNameState())

	val userNameState = merge(_userNameState.map { it.name }, _localName)
		.map { name ->
			ChangeUserNameState(name = name, showDialog = _userNameState.value.showDialog)
		}.stateIn(
			viewModelScope,
			SharingStarted.Eagerly,
			initialValue = ChangeUserNameState()
		)

	fun onUserNameEvents(event: UserNameEvents) {
		when (event) {
			is UserNameEvents.OnUserNameChange -> _userNameState.update { state ->
				state.copy(name = event.value)
			}

			UserNameEvents.OnNameChangeConfirm -> saveUserName()
			UserNameEvents.ToggleUserNameDialog -> _userNameState.update { state ->
				state.copy(showDialog = !state.showDialog)
			}
		}
	}


	private fun saveUserName() = viewModelScope.launch {
		preferences.setPlayerUserName(_userNameState.value.name)
	}

	fun onCheckRoomEvents(event: RoomInteractionEvents) {
		when (event) {
			is RoomInteractionEvents.OnValueChange -> _checkRoomState.update { state ->
				state.copy(roomId = event.count)
			}

			RoomInteractionEvents.RoomDataRequest -> onJoinRoom(_checkRoomState.value.roomId)

			RoomInteractionEvents.OnDialogToggle -> _checkRoomState.update { state ->
				state.copy(showDialog = !_checkRoomState.value.showDialog)
			}

			is RoomInteractionEvents.ToggleLoadingState -> _checkRoomState.update { state ->
				state.copy(isLoading = event.state)
			}

			is RoomInteractionEvents.OnDialogConfirm -> {}
		}
	}

	fun onCreateRoomEvents(event: RoomInteractionEvents) {
		when (event) {
			is RoomInteractionEvents.OnValueChange -> _createRoomState.update { state ->
				state.copy(boardCount = event.count)
			}

			RoomInteractionEvents.RoomDataRequest -> _createRoomState.value.boardCount.toIntOrNull()
				?.let { board -> onCreateRoom(board) } ?: onCreateRoom()

			RoomInteractionEvents.OnDialogToggle -> _createRoomState.update { state ->
				state.copy(showDialog = !_createRoomState.value.showDialog)
			}

			is RoomInteractionEvents.ToggleLoadingState -> _createRoomState.update { state ->
				state.copy(isLoading = event.state)
			}

			is RoomInteractionEvents.OnDialogConfirm -> {
				_createRoomState.update { state -> state.copy(showDialog = false) }
				_checkRoomState.update { state -> state.copy(roomId = event.roomId) }
				viewModelScope.launch {
					_uiEvents.emit(UiEvents.Navigate(route = ApiPaths.CHECK_ROOM_PATH))
				}
			}
		}
	}

	private fun onCreateRoom(board: Int = 1) = viewModelScope.launch(Dispatchers.IO) {
		playerRepo.createRoom(board).onEach { res ->
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


	private fun onJoinRoom(roomId: String) = viewModelScope.launch(Dispatchers.IO) {
		playerRepo.joinRoom(roomId).onEach { res ->
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
							response = res.data.roomModel,
							message = res.data.message
						)
					}
				}
			}
		}.launchIn(this)
	}
}
