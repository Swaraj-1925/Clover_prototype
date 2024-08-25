package com.clovermusic.clover.domain.usecase.album

import android.util.Log
import com.clovermusic.clover.data.local.entity.relations.AlbumWithTrack
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAlbumUseCase @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val repository: Repository
) {
    operator fun invoke(albumId: String): Flow<DataState<AlbumWithTrack>> =
        flow {
            try {

                dataSource.authData.ensureValidAccessToken()
                emit(DataState.Loading)
                emit(
                    DataState.NewData(
                        repository.album.getAlbum(albumId)
                    )
                )
            } catch (e: Exception) {
                val error = customErrorHandling(e)
                Log.e("getAlbumUseCase", "Getting specific album", e)
                emit(DataState.Error(error))
            }
        }
}