package com.clovermusic.clover.data.spotify.appRemote

import android.content.Context
import android.util.Log
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_ID
import com.clovermusic.clover.util.SpotifyAuthConfig.REDIRECT_URI
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.SpotifyDisconnectedException
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SpotifyAppRemoteRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun assertAppRemoteConnected(spotifyAppRemote: SpotifyAppRemote): SpotifyAppRemote {
        spotifyAppRemote.let {
            if (it.isConnected) {
                return it
            }
        }
        Log.e("SpotifyAppRemote", "Error connecting to Spotify App Remote")
        throw SpotifyDisconnectedException()
    }

    suspend fun connectToAppRemote(): SpotifyAppRemote =
        suspendCoroutine { cont: Continuation<SpotifyAppRemote> ->
            try {
                SpotifyAppRemote.connect(
                    context,
                    ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build(),
                    object : Connector.ConnectionListener {
                        override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                            cont.resume(spotifyAppRemote)
                        }

                        override fun onFailure(error: Throwable) {
                            cont.resumeWithException(error)
                        }
                    })
            } catch (error: Throwable) {
                throw CustomException.AuthException("SpotifyAppRemote", "connectRemote", error)
            } catch (e: IOException) {
                throw CustomException.NetworkException("SpotifyAppRemote", "connectRemote", e)
            } catch (error: Exception) {
                throw CustomException.UnknownException(
                    "SpotifyAppRemote",
                    "connectRemote",
                    "Unknown error",
                    error
                )
            }
        }


}