package com.clovermusic.clover.util

import android.database.sqlite.SQLiteException
import retrofit2.HttpException
import java.io.IOException

fun customErrorHandling(e: Exception): String {
    return when (e) {
        // Network errors
        is IOException -> "Unable to connect to the internet. Please check your connection."

        // HTTP errors
        is HttpException -> when (e.code()) {
            400 -> "Invalid request. Please try again."
            401 -> "Unauthorized access. Please log in."
            403 -> "Access denied. You don't have permission."
            404 -> "Resource not found. Please try again."
            500 -> "Server error. Please try again later."
            else -> "Network error. Please try again."
        }

        // SQLite/Room database errors
        is SQLiteException -> "Database error. Please try again."
        is IllegalArgumentException -> "Invalid input provided. Please check your input and try again."
        is NullPointerException -> "An unexpected error occurred. Please restart the app and try again."
        // General errors
        else -> e.message ?: "The app has run out of memory. Please close other apps and try again."
    }
}
