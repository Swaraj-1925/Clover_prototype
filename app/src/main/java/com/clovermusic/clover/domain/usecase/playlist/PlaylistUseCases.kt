package com.clovermusic.clover.domain.usecase.playlist

import javax.inject.Inject

data class PlaylistUseCases @Inject constructor(
    val addItemsToPlaylist: AddItemsToPlaylistUseCase,
    val createNewPlaylist: CreateNewPlaylistUseCase,
    val getPlaylistItems: GetPlaylistItemsUseCase,
    val getPlaylist: GetPlaylistUseCase,
    val removePlaylistItems: RemovePlaylistItemsUseCase,
    val uploadPlaylistCover: UploadPlaylistCoverUseCase,
    val currentUserPlaylist: CurrentUsersPlaylistsUseCase
)

