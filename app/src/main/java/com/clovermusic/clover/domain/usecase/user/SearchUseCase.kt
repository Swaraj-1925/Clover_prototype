package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.local.entity.SearchResultEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.domain.model.Search
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
class SearchUseCase @Inject constructor(
    private val dataAction: NetworkDataAction,
    private val repository: Repository
) {
    suspend fun search(
        query: String,
        type: List<String>,
        limit: Int
    ): Flow<DataState<Search>> = flow {
        emit(DataState.Loading)
        try {
            dataAction.authData.ensureValidAccessToken()
            val response = repository.user.search(query, type, limit)
            val search = Search(
                album = response.albums?.items?.toEntity() ?: emptyList(),
                artist = response.artists?.items?.toEntity() ?: emptyList(),
                playlist = response.playlists?.items?.toEntity() ?: emptyList(),
                track = response.tracks?.items?.toEntity() ?: emptyList()
            )
            emit(DataState.NewData(search))
        } catch (e: Exception) {
            Log.d("SearchUseCase", "${e.message}")
            emit(DataState.Error(customErrorHandling(e)))
        }
    }.flowOn(Dispatchers.IO)

    fun getSearchHistory(): Flow<DataState<List<SearchResultEntity>>> = flow {
        emit(DataState.Loading)
        try {
            val history = repository.user.getStoredSearchResults()
            emit(DataState.OldData(history))
        } catch (e: Exception) {
            Log.d("SearchUseCase", "${e.message}")
            emit(DataState.Error(customErrorHandling(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun storeClickedSearchResult(clickedResult: Search) {
        try {
            repository.user.storeSearchResult(clickedResult)
        } catch (e: Exception) {
            Log.d("SearchUseCase", "Error storing clicked search result: ${e.message}")
        }
    }
}
