package com.example.trendy.presentation.use_cases

import arrow.core.Either
import com.example.trendy.common.ErrorType
import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.data.data_sources.models.MovieDetail

interface FavouriteMoviesUseCase {
    suspend fun loadMovies() : Either<ErrorType, List<MovieDetail>>
}