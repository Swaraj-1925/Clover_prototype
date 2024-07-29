package com.clovermusic.clover.data.spotify.appRemote

import android.content.Context
import android.util.Log
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_ID
import com.clovermusic.clover.util.SpotifyAuthConfig.REDIRECT_URI
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import javax.inject.Inject

class SpotifyAppRemoteRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var spotifyAppRemote: SpotifyAppRemote? = null

    suspend fun getConnectedAppRemote(): SpotifyAppRemote {
        return spotifyAppRemote ?: connectToAppRemote().also { spotifyAppRemote = it }
    }

    private suspend fun connectToAppRemote(): SpotifyAppRemote =
        suspendCancellableCoroutine { cont ->
            val connectionParams = ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build()

            SpotifyAppRemote.connect(
                context,
                connectionParams,
                object : Connector.ConnectionListener {
                    override fun onConnected(appRemote: SpotifyAppRemote) {
                        cont.resumeWith(Result.success(appRemote))
                    }

                    override fun onFailure(error: Throwable) {
                        Log.e("SpotifyAppRemote", "Error connecting to Spotify App Remote", error)
                        cont.resumeWith(
                            Result.failure(
                                when (error) {
                                    is IOException -> CustomException.NetworkException(
                                        "SpotifyAppRemote",
                                        "connectRemote",
                                        error
                                    )

                                    else -> CustomException.UnknownException(
                                        "SpotifyAppRemote",
                                        "connectRemote",
                                        "Unknown error",
                                        error
                                    )
                                }
                            )
                        )
                    }
                })

            cont.invokeOnCancellation {
                spotifyAppRemote?.let {
                    SpotifyAppRemote.disconnect(it)
                }
            }
        }
}
