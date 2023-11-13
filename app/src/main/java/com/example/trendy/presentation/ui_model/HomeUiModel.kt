package com.example.trendy.presentation.ui_model

import com.example.trendy.data.data_sources.models.Movie

data class HomeUiModel (
    val upcoming: List<Movie>,
    val popular: List<Movie>,
)