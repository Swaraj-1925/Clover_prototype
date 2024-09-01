package com.clovermusic.clover.presentation.composable.searchScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.SearchResultEntity
import com.clovermusic.clover.domain.model.Search
import com.clovermusic.clover.presentation.composable.components.AlbumSearchCard
import com.clovermusic.clover.presentation.composable.components.ArtistSearchCard
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar
import com.clovermusic.clover.presentation.composable.components.PlaylistSearchCard
import com.clovermusic.clover.presentation.composable.components.SearchBar
import com.clovermusic.clover.presentation.composable.components.TrackSearchCard
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.presentation.viewModel.SearchViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import com.clovermusic.clover.util.DataState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
    navController: NavController
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategories by viewModel.selectedCategories.collectAsStateWithLifecycle()
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val systemUiController = rememberSystemUiController()

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            CloverTheme {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SearchBar(
                    searchText = searchText,
                    onSearchTextChanged = viewModel::onSearchTextChange,
                    navController = navController
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategories.contains(category),
                            onClick = { viewModel.toggleCategory(category) },
                            label = { Text(category) },
                            modifier = Modifier.padding(horizontal = 8.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.surface
                            ),
                            border = null,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                when {
                    searchText.isEmpty() -> SearchHistoryList(searchHistory, snackbarHostState)
                    else -> SearchResultsList(searchResults, snackbarHostState, navController)
                }
            }
            }


        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            Column {
                if (playbackState is PlaybackState.Playing || playbackState is PlaybackState.Paused) {
                    PlayingSongBar(navController = navController)
                }
                NavigationBar(navController = navController)
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
            }
        }
    }
}


//in History only few usefully things are shown image, name,  id , uri , ps Track Artist is not saved locally in history
@Composable
fun SearchHistoryList(searchHistory: DataState<List<SearchResultEntity>>,snackbarHostState: SnackbarHostState) {
    when (searchHistory) {
        is DataState.Loading -> {}
        is DataState.NewData, is DataState.OldData -> {
            val history = when (searchHistory) {
                is DataState.NewData -> searchHistory.data
                is DataState.OldData -> searchHistory.data
                else -> emptyList()
            }
            LazyColumn {
                items(history) { historyItem ->
                    Text(
                        text = historyItem.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
        is DataState.Error -> {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = searchHistory.message,
                    duration = SnackbarDuration.Long
                )
            }
        }
    }
}

//List of search results Don't have to be plane text  show card for different types
// it dose not show all in category header see if can figure out way to do that
@Composable
fun SearchResultsList(searchResults: DataState<Search>, snackbarHostState: SnackbarHostState,
                      navController: NavController,) {
    when (searchResults) {
        is DataState.Loading -> {
            LoadingAnimation()
        }
        is DataState.NewData -> {
            val results = searchResults.data
            LazyColumn {
                if (!results.track.isNullOrEmpty()) {
                    item { CategoryHeader("Tracks") }
                    items(results.track) { track ->
                        TrackSearchCard(track = track )
                    }
                }
                if (!results.artist.isNullOrEmpty()) {
                    item { CategoryHeader("Artists") }
                    items(results.artist) { artist ->
                        ArtistSearchCard(
                            artistName = artist,
                            url = artist.imageUrl,
                            navController = navController)
                    }
                }
                if (!results.album.isNullOrEmpty()) {
                    item { CategoryHeader("Albums") }
                    items(results.album) { album ->
                        AlbumSearchCard(
                            album = album,
                            navController = navController
                        )
                    }
                }
                if (!results.playlist.isNullOrEmpty()) {
                    item { CategoryHeader("Playlists") }
                    items(results.playlist) { playlist ->
                        PlaylistSearchCard(
                            playlist = playlist,
                            navController = navController,
                            songCount = playlist.totalTrack)
                    }
                }
            }
        }
        is DataState.Error -> {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = searchResults.message,
                    duration = SnackbarDuration.Long
                )
            }
        }
        else -> Text("No results found.")
    }
}

@Composable
fun CategoryHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SearchResultItem(name: String) {
    Text(
        text = name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}