package com.darkabhi.appinesstask.common

sealed class State {
    data class Success<T>(val data: T? = null) : State()
    data class Failed(val message: String?) : State()
    object Loading : State()
    object Empty : State()
}