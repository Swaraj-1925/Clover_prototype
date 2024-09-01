package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.dto.playlists.CreatePlaylistRequest
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.Resource
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewPlaylistUseCase @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
    suspend operator fun invoke(
        userId: String,
        playlistRequest: CreatePlaylistRequest
    ): Flow<Resource<String>>  =flow{
        try {
            emit(Resource.Loading())
            networkDataAction.authData.ensureValidAccessToken()
            networkDataAction.playlistData.createNewPlaylist(userId, playlistRequest)
            emit(Resource.Success("Playlist created successfully"))
        } catch (e: Exception) {
            val error = customErrorHandling(e)
            emit(Resource.Error(error))
        }
    }
}