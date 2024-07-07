package com.clovermusic.clover.domain.usecase.playlist

data class PlaylistUseCases(
    val playlistItems: PlaylistItemsUseCase,
    val currentUsersPlaylists: CurrentUsersPlaylistsUseCase,
    val playlistUseCase: PlaylistUseCase
)

