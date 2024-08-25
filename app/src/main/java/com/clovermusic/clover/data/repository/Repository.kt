package com.clovermusic.clover.data.repository

import javax.inject.Inject

data class Repository @Inject constructor(
    val artists: ArtistRepository,
    val playlists: PlaylistRepository,
    val user: UserRepository,
    val album: AlbumRepository
)
