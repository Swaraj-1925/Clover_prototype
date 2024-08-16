package com.clovermusic.clover.domain.usecase.artist

import android.provider.ContactsContract.Data
import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistInfoUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val networkDataSource: NetworkDataSource,
) {
    suspend operator fun invoke(artistId: String ) : Flow<DataState<ArtistsEntity>> = flow {
        try {
            emit(DataState.Loading)

            networkDataAction.authData.ensureValidAccessToken()
            val fetchArtist = networkDataSource.artistData.fetchArtist(artistId)

            emit(DataState.NewData(fetchArtist))
        } catch (e: Exception) {
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)

        }

    }
}