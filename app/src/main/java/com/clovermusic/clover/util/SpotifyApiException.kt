package com.clovermusic.clover.util

import android.util.Log
import retrofit2.HttpException

object SpotifyApiException {
    fun handleApiException(repository: String, functionName: String, e: HttpException): Nothing {
        Log.e("handleApiException for $repository", " at $functionName: HttpException", e)
        when (e.code()) {
            401 -> throw CustomException.ApiException(
                repository,
                functionName,
                "Unable to authenticate with Spotify. Please try again later.",
                e
            )

            429 -> throw CustomException.ApiException(
                repository,
                functionName,
                "We are having problems with our servers. Please try again later.",
                e
            )

            400 -> throw CustomException.ApiException(
                repository,
                functionName,
                "We are unable to connect to your Spotify account. Please try again later."
            )

            403 -> throw CustomException.ApiException(
                repository,
                functionName,
                "We are unable to connect to your Spotify Server. Please try again later."
            )

            else -> throw CustomException.ApiException(
                repository,
                functionName,
                "API error: ${e.code()} ${e.message()}",
                e
            )
        }
    }
}