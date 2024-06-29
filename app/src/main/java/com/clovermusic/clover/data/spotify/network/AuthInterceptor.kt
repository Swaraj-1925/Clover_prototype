package com.clovermusic.clover.data.spotify.network

import com.clovermusic.clover.data.spotify.persistence.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * configures header , add access token as header
 */
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        tokenManager.getAccessToken()?.let { accessToken ->
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(requestBuilder.build())
    }
}