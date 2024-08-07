package com.clovermusic.clover.data.spotify.api.networkDataAction

import javax.inject.Inject

data class NetworkDataAction @Inject constructor(
    val authData: AuthDataAction,
    val playlistData: PlaylistDataAction,
    val userData: UserDataAction
)