package com.momentolabs.app.security.cleanarchitecture.utils

sealed class Resources<out T> {
    data object onLoading : Resources<Nothing>()
    data class onError(val e: Exception) : Resources<Nothing>()
    data class onSuccess<out T>(val data: T) : Resources<T>()
}
