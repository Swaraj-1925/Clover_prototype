package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.repository.ArtistRepository
import com.clovermusic.clover.domain.mapper.toTrack
import com.clovermusic.clover.domain.model.Track
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistsTopTracksUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val networkDataAction: NetworkDataAction
) {
    //    Get top tracks of Artists
    suspend operator fun invoke(artistId: String): Flow<DataState<List<TrackEntity>>> = flow {
        try {
            emit(DataState.Loading)

            val track = mutableListOf<TrackEntity>()
            networkDataAction.authData.ensureValidAccessToken()
            val res = artistRepository.getArtistTopTracks(artistId)

            track.addAll(res)
            emit(DataState.NewData(track))
        }catch (e:Exception){
            val error = customErrorHandling(e)
            emit(DataState.Error(error))
            Log.e("ArtistsTopTracksUseCase", "Error fetching top tracks for artist", e)
        }
    }
}