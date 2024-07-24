package com.clovermusic.clover.domain.usecase.playlist

data class PlaylistUseCases(
    val addItemsToPlaylist: AddItemsToPlaylistUseCase,
    val createNewPlaylist: CreateNewPlaylistUseCase,
    val getPlaylistItems: GetPlaylistItemsUseCase,
    val getPlaylist: GetPlaylistUseCase,
    val removePlaylistItems: RemovePlaylistItemsUseCase,
    val uploadPlaylistCover: UploadPlaylistCoverUseCase,
    val currentUserPlaylist: CurrentUsersPlaylistsUseCase
)

