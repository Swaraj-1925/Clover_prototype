package com.clovermusic.clover.util

import retrofit2.HttpException

object SpotifyApiException {
    fun handleApiException(repository: String, functionName: String, e: HttpException): Nothing {
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

            else -> throw CustomException.ApiException(
                repository,
                functionName,
                "API error: ${e.code()} ${e.message()}",
                e
            )
        }
    }
}