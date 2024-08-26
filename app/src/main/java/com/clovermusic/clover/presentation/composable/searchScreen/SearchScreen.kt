package com.clovermusic.clover.presentation.composable.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.SearchResultEntity
import com.clovermusic.clover.domain.model.Search
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.presentation.viewModel.SearchViewModel
import com.clovermusic.clover.util.DataState

//TODO : DELETE ALL COMMENTS  AFTER COMPLETING SCREEN

//this is main componat
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
    navController: NavController
) {
//    shows text after typing in text filed
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()

//    to know if user is searching i.e started typing in text field
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()

    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

//    get old data from db
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()

//    List of categories
    val categories by viewModel.categories.collectAsStateWithLifecycle()

//    List of selected categories
    val selectedCategories by viewModel.selectedCategories.collectAsStateWithLifecycle()

//    for bottom bar
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            SearchBar(
                query = searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = { viewModel.search(it) },
                active = isSearching,
                onActiveChange = { viewModel.toggleIsSearching(it) },
                placeholder = {
                    Text(
                        text = "What would you like to hear",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            ) {
//                Data to show After User Starts Typing in TextField
                Column {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(categories) { category ->
                            FilterChip(
                                selected = selectedCategories.contains(category),
                                onClick = { viewModel.toggleCategory(category) },
                                label = { Text(category) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when {
//                        Show old data if there is no search results or user didn't type anything
                        searchText.isEmpty() -> SearchHistoryList(searchHistory,snackbarHostState)
//                        Show search results if user typed something
                        else -> SearchResultsList(searchResults,snackbarHostState)
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            Column {
                if (playbackState is PlaybackState.Playing || playbackState is PlaybackState.Paused) {
                    PlayingSongBar(
                        navController = navController
                    )
                }
                NavigationBar(navController = navController)
            }
        },
    ) { innerPadding ->
//        Everything else which is supposed be in SearchScreen
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
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
fun SearchResultsList(searchResults: DataState<Search>,snackbarHostState: SnackbarHostState) {
    when (searchResults) {
        is DataState.Loading -> {}
        is DataState.NewData -> {
            val results = searchResults.data
            LazyColumn {
                if (!results.album.isNullOrEmpty()) {
                    item { CategoryHeader("Albums") }
                    items(results.album) { album -> SearchResultItem(album.name) }
                }
                if (!results.artist.isNullOrEmpty()) {
                    item { CategoryHeader("Artists") }
                    items(results.artist) { artist -> SearchResultItem(artist.name) }
                }
                if (!results.playlist.isNullOrEmpty()) {
                    item { CategoryHeader("Playlists") }
                    items(results.playlist) { playlist -> SearchResultItem(playlist.name) }
                }
                if (!results.track.isNullOrEmpty()) {
                    item { CategoryHeader("Tracks") }
                    items(results.track) { track -> SearchResultItem(track.name) }
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