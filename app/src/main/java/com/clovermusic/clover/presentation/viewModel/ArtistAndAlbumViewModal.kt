package com.clovermusic.clover.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.data.local.entity.relations.AlbumWithTrack
import com.clovermusic.clover.domain.usecase.album.AlbumUseCases
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.presentation.composable.components.BottomSheetOption
import com.clovermusic.clover.presentation.uiState.ArtistDataUiState
import com.clovermusic.clover.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistAndAlbumViewModal @Inject constructor(
    private val artist: ArtistUseCases,
    private val albumsUseCase: AlbumUseCases
) : ViewModel() {

    private val _artistData = MutableStateFlow<DataState<ArtistDataUiState>>(DataState.Loading)
    val artistData: StateFlow<DataState<ArtistDataUiState>> = _artistData.asStateFlow()

    private val _album = MutableStateFlow<DataState<AlbumWithTrack>>(DataState.Loading)
    val album: StateFlow<DataState<AlbumWithTrack>> = _album.asStateFlow()



    fun getArtistData(artistId: String, forceRefresh: Boolean, limit: Int? = null) {
        viewModelScope.launch {
            combine(
                artist.getArtist(artistId),
                artist.artistAlbums(
                    artistIds = listOf(artistId),
                    limit = limit,
                    forceRefresh = forceRefresh
                ),
                artist.artistTopTrack(artistId)
            ) { artistInfoState, artistAlbumsState, artistTopTracksState ->
                when {
                    artistInfoState is DataState.NewData &&
                            artistAlbumsState is DataState.NewData &&
                            artistTopTracksState is DataState.NewData -> {
                        DataState.NewData(
                            ArtistDataUiState(
                                artistInfoState.data,
                                artistAlbumsState.data,
                                artistTopTracksState.data
                            )
                        )
                    }

                    artistInfoState is DataState.Error -> DataState.Error(artistInfoState.message)
                    artistAlbumsState is DataState.Error -> DataState.Error(artistAlbumsState.message)
                    artistTopTracksState is DataState.Error -> DataState.Error(artistTopTracksState.message)
                    else -> DataState.Loading
                }
            }.collect { combinedState ->
                _artistData.value = combinedState
            }

        }
    }

    fun getAlbum(albumId: String) {
        viewModelScope.launch {
            val albums = albumsUseCase.getAlbums(albumId)
            albums.collect { state ->
                when (state) {
                    is DataState.Error -> _album.value = DataState.Error(state.message)
                    is DataState.Loading -> _album.value = DataState.Loading
                    is DataState.NewData -> _album.value = DataState.NewData(state.data)
                    is DataState.OldData -> _album.value = DataState.OldData(state.data)
                }
            }
        }
    }

}
