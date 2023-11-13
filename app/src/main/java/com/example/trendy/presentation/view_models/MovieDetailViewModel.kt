package com.example.trendy.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendy.common.UiState
import com.example.trendy.data.data_sources.models.MovieDetail
import com.example.trendy.domain.use_cases.UpComingMoviesUseCase
import com.example.trendy.domain.use_cases.PopularMoviesUseCase
import com.example.trendy.presentation.ui_model.HomeUiModel
import com.example.trendy.presentation.use_cases.MovieDetailUseCase
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
class MovieDetailViewModel @Inject constructor(
    private val useCase: MovieDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _data = MutableStateFlow<MovieDetail?>(null)
    val data: StateFlow<MovieDetail?> = _data.asStateFlow()

    fun loadMoviesDetail(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = UiState.Loading
                delay(3000)
                useCase.loadMovieDetail(movieId).fold({
                    _uiState.value = UiState.Error(it.code)
                },{
                    _data.value = it
                    _uiState.value = UiState.Success
                })
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                data.value?.let {
                    useCase.save(it)
                    it.isSaved.value = true
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                data.value?.let {
                    useCase.delete(it)
                    it.isSaved.value = false
                }
            }
        }
    }

}