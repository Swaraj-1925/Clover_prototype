package com.clovermusic.clover.util

import retrofit2.HttpException

object SpotifyApiException {
    fun handleApiException(repository: String, functionName: String, e: HttpException): Nothing {
        when (e.code()) {
            401 -> throw CustomException.ApiException(
                "UserRepository",
                "getFollowedArtists",
                "Unauthorized: The request requires user authentication or authorization has been refused.",
                e
            )

            429 -> throw CustomException.ApiException(
                "UserRepository",
                "getFollowedArtists",
                "Too Many Requests : There are to many requests in a short period of time.",
                e
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