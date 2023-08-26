package com.eva.androidtictactoe.presentation.utils

sealed interface OnBoardingEvents {

    data class OnUserNameChanged(val name: String) : OnBoardingEvents

    object OnAnonymousRoomJoin : OnBoardingEvents

    object OnCreateRoom : OnBoardingEvents
}