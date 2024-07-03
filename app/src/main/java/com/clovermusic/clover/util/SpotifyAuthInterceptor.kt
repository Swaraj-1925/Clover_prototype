package com.clovermusic.clover.util

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SpotifyAuthInterceptor @Inject constructor(
    private val tokenManager: SpotifyTokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        tokenManager.getAccessToken()?.let { accessToken ->
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(requestBuilder.build())
    }
}