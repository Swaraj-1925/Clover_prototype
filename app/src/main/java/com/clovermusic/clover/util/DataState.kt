package com.clovermusic.clover.util

sealed class DataState<out T> {
    data class OldData<T>(val data: T) : DataState<T>()
    data class NewData<T>(val data: T) : DataState<T>()
    data class Error(val message: String) : DataState<Nothing>()
}