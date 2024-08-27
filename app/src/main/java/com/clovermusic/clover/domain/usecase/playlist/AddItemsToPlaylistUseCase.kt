package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.customErrorHandling
import javax.inject.Inject

class AddItemsToPlaylistUseCase @Inject constructor(
    private val repository: Repository,
    private val dataAction: NetworkDataAction
) {
    suspend operator fun invoke(playlistId: String, uris: List<String>) {
        try {

        }catch (e: Exception){
            val error = customErrorHandling(e)

        }
    }
}