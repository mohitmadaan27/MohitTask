package com.nagarro.mohitdemo.domain

sealed class MovieResourceState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): MovieResourceState<T>(data)
    class Error<T>(message: String, data:T? = null): MovieResourceState<T>(data, message)
    class Loading<T>: MovieResourceState<T>()
    class Empty<T>: MovieResourceState<T>()
}