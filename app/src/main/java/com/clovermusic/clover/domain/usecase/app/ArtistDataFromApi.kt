package com.clovermusic.clover.domain.usecase.app

import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import javax.inject.Inject

class ArtistDataFromApi @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val networkDataSource: NetworkDataSource
) {
//
//    operator fun invoke(
//        artistId: String
//    ): Flow<DataState<ArtistScreenUiState>> = flow {
//        try {
//            emit(DataState.Loading)
//            networkDataAction.authData.ensureValidAccessToken()
//
//            val artistInfo = networkDataSource.artistData.fetchArtist(artistId)
//            Log.d("ArtistDataFromApi", "invoke: $artistInfo")
//            val artistAlbums =
//                networkDataSource.artistData.fetchArtistAlbums(artistId, 5).toArtistAlbums()
//            Log.d("ArtistDataFromApi", "invoke: $artistAlbums")
//            val artistTopTracks =
//                networkDataSource.artistData.fetchArtistTopTracks(artistId).toEntityWithArtists()
//            Log.d("ArtistDataFromApi", "invoke: $artistTopTracks")
//
//            emit(
//                DataState.NewData(
//                    ArtistScreenUiState(
//                        artistInfo = artistInfo,
//                        artistAlbums = artistAlbums,
//                        artistTopTracks = artistTopTracks
//                    )
//                )
//            )
//
//        } catch (error: Exception) {
//            val e = customErrorHandling(error)
//            emit(
//                DataState.Error(e)
//            )
//        }
//    }
}
