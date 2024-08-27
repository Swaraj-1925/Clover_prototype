package com.clovermusic.clover.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute


@Serializable
data class PlaylistScreenRoute(
    val id: String,
)

@Serializable
object MusicPlayerScreenRoute

@Serializable
object PlayingSongBarRoute


@Serializable
data class ArtistScreenRoute(
    val id: String,
)

@Serializable
data class ArtistAlbumScreenRoute(
    val id: String,
)

@Serializable
object LibraryScreenRoute