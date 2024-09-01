package com.clovermusic.clover.data.spotify.api.networkDataSources


import javax.inject.Inject

data class NetworkDataSource @Inject constructor(
    val authData: AuthDataSource,
    val playlistData: PlaylistDataSource,
    val userData: UserDataSource,
    val artistData: ArtistDataSource,
    val albumData: AlbumDataSources
)