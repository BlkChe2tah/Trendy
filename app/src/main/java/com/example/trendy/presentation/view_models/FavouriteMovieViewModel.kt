package com.example.trendy.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendy.common.UiState
import com.example.trendy.data.data_sources.models.MovieDetail
import com.example.trendy.presentation.use_cases.FavouriteMoviesUseCase
import com.example.trendy.presentation.use_cases.MovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouriteMovieViewModel @Inject constructor(
    private val useCase: FavouriteMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _movies = MutableStateFlow<List<MovieDetail>>(listOf())
    val movies: StateFlow<List<MovieDetail>> = _movies.asStateFlow()

    fun loadFavouriteMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = UiState.Loading
                delay(3000)
                useCase.loadMovies().fold({
                    _uiState.value = UiState.Error(it.code)
                },{
                    if (it.isEmpty()) {
                        _uiState.value = UiState.Empty
                        return@withContext
                    }
                    _movies.value = it
                    _uiState.value = UiState.Success
                })
            }
        }
    }

}