package com.eva.androidtictactoe.utils

sealed interface Resource<T> {

    data class Success<T>(val data: T, val message: String? = null) : Resource<T>

    class Loading<T> : Resource<T>

    data class Error<T>(val message: String, val data: T? = null) : Resource<T>
}