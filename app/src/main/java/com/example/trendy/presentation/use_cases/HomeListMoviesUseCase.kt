package com.example.trendy.presentation.use_cases

import arrow.core.Either
import com.example.trendy.common.ErrorType
import com.example.trendy.data.data_sources.models.Movie

interface HomeListMoviesUseCase {
    suspend fun loadMovies() : Either<ErrorType, List<Movie>>
    suspend fun loadMoviesFromStorage() : Either<ErrorType, List<Movie>>
}