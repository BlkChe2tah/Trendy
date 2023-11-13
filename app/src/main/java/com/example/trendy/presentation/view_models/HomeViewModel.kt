package com.example.trendy.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendy.common.UiState
import com.example.trendy.domain.use_cases.UpComingMoviesUseCase
import com.example.trendy.domain.use_cases.PopularMoviesUseCase
import com.example.trendy.presentation.ui_model.HomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val popular: PopularMoviesUseCase,
    private val upcoming: UpComingMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _movies = MutableStateFlow<HomeUiModel?>(null)
    val movies: StateFlow<HomeUiModel?> = _movies.asStateFlow()

    fun loadMovies(network: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = UiState.Loading
                delay(3000)
                val upcoming = async { if (network) upcoming.loadMovies() else upcoming.loadMoviesFromStorage() }
                val popular = async { if (network) popular.loadMovies() else popular.loadMoviesFromStorage() }
                val upcomingResult = upcoming.await()
                val popularResult = popular.await()
                if (upcomingResult.isLeft() && popularResult.isLeft()) {
                    _uiState.value = UiState.Error(-1)
                    return@withContext
                }
                val result = HomeUiModel(
                    upcoming = upcomingResult.getOrNone().getOrNull() ?: emptyList(),
                    popular = popularResult.getOrNone().getOrNull() ?: emptyList(),
                )
                if (result.isMoviesEmpty()) {
                    _uiState.value = UiState.Empty
                    return@withContext
                }
                _movies.value = result
                _uiState.value = UiState.Success
            }
        }
    }

}

fun HomeUiModel.isMoviesEmpty() : Boolean {
    return popular.isEmpty() && upcoming.isEmpty()
}