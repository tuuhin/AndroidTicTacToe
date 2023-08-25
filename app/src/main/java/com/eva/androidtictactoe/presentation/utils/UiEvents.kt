package com.eva.androidtictactoe.presentation.utils

sealed interface UiEvents {
    data class ShowSnackBar(val message: String) : UiEvents

    data class Navigate(val route: String) : UiEvents
}