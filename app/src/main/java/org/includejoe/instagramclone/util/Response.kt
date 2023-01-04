package org.includejoe.instagramclone.util

sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<out T>(val data : T): Response<T>()
    data class Error<out T>(val message : String): Response<Nothing>()
}
