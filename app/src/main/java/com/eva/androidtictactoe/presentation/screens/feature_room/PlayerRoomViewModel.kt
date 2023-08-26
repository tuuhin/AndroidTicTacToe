package com.eva.androidtictactoe.presentation.screens.feature_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.androidtictactoe.data.local.UserPreferencesFacade
import com.eva.androidtictactoe.domain.repository.PlayerRoomRepository
import com.eva.androidtictactoe.presentation.utils.OnBoardingEvents
import kotlinx.coroutines.launch

class PlayerRoomViewModel(
    private val repo: PlayerRoomRepository,
    private val preferences: UserPreferencesFacade
) : ViewModel() {

    val userName = preferences.playerUserName

    fun onEvents(event: OnBoardingEvents) {
        when (event) {
            OnBoardingEvents.OnAnonymousRoomJoin -> {}
            OnBoardingEvents.OnCreateRoom -> {}
            is OnBoardingEvents.OnUserNameChanged -> {
                viewModelScope.launch {
                    preferences.setPlayerUserName(event.name)
                }
            }
        }
    }
}