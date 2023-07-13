package com.nagarro.mohitdemo.domain

sealed class LoginResultState<out T : Any> {

    data class Success<out T : Any>(val data: T) : LoginResultState<T>()
    data class Error(val exception: Exception) : LoginResultState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}