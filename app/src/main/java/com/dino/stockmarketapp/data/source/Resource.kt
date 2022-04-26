package com.dino.stockmarketapp.data.source

sealed class Resource<out T> {
    data class Success<T>(val value: T?): Resource<T>()
    data class Error<T>(
        val message: String,
        val data: T? = null
    ): Resource<T>()

    data class Loading(val isLoading: Boolean): Resource<Nothing>()
    object Empty: Resource<Nothing>()
}