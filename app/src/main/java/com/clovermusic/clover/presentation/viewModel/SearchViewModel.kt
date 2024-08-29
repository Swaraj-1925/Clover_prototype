package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.SearchResultEntity
import com.clovermusic.clover.domain.model.Search
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchResults = MutableStateFlow<DataState<Search>>(DataState.OldData(Search()))
    val searchResults = _searchResults.asStateFlow()

    private val _searchHistory = MutableStateFlow<DataState<List<SearchResultEntity>>>(DataState.Loading)
    val searchHistory = _searchHistory.asStateFlow()

    private val _categories = MutableStateFlow(listOf("All", "Album", "Artist", "Playlist", "Track"))
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _selectedCategories = MutableStateFlow(setOf("All"))
    val selectedCategories: StateFlow<Set<String>> = _selectedCategories.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadSearchHistory()
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        if (text.isNotEmpty()) {
            search(text)
        } else {
            _searchResults.value = DataState.OldData(Search())
        }
    }

    fun toggleIsSearching(state: Boolean) {
        _isSearching.value = state
    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                toggleIsSearching(true)
                _searchResults.value = DataState.Loading
                delay(250)
                if (query.isNotEmpty()) {
                    val categories = _selectedCategories.value.toList().let {
                        if (it.contains("All")) listOf("All") else it
                    }
                    userUseCases.searchUseCase.search(query, categories, 10)
                        .collect { state ->
                            _searchResults.value = state
                        }
                } else {
                    _searchResults.value = DataState.NewData(
                        Search(emptyList(), emptyList(), emptyList(), emptyList())
                    )
                }
            } catch (e: Exception) {
                Log.d("SearchViewModel", "search: ${e.message}")
                _searchResults.value = DataState.Error(e.message ?: "Unknown error occurred")
            } finally {
//                toggleIsSearch(false)
            }
        }
    }

    fun toggleCategory(category: String) {
        _selectedCategories.update { currentSet ->
            when {
                category == "All" -> setOf("All")
                currentSet.contains(category) -> {
                    val newSet = currentSet - category
                    if (newSet.isEmpty()) setOf("All") else newSet
                }
                else -> {
                    val newSet = currentSet + category
                    newSet - "All"
                }
            }
        }

        if (_searchText.value.isNotEmpty()) {
            search(_searchText.value)
        }
    }
    private fun loadSearchHistory() {
        viewModelScope.launch {
            userUseCases.searchUseCase.getSearchHistory().collect { state ->
                _searchHistory.value = state
            }
        }
    }
    fun onClearClick(
    ) {
        _searchText.value = ""
            _searchResults.value = DataState.OldData(Search())
        }

}