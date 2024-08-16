package com.clovermusic.clover.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.presentation.navigation.ArtistAlbumScreenRoute
import com.clovermusic.clover.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModal @Inject constructor(
    private val artist : ArtistUseCases
) : ViewModel() {

    private val _artistInfo = MutableStateFlow<DataState<ArtistsEntity>>(DataState.Loading)
    val artistInfo: StateFlow<DataState<ArtistsEntity>> = _artistInfo.asStateFlow()

    fun getArtistInfo(artistId:String){
        viewModelScope.launch {
            val data = artist.getArtist(artistId)
            data.collect { state ->
                when (state) {
                    is DataState.Error -> {
                        _artistInfo.value = DataState.Error(state.message)
                    }
                    DataState.Loading -> _artistInfo.value = DataState.Loading
                    is DataState.NewData -> {
                        _artistInfo.value = DataState.NewData(state.data)
                    }

                   else -> {}
                }
            }
        }
    }






}
