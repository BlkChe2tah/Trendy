package com.example.trendy.domain.use_cases

import arrow.core.Either
import com.example.trendy.common.ErrorType
import com.example.trendy.common.defaultDataCallHandler
import com.example.trendy.common.defaultNetworkCallHandler
import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.domain.repositories.MoviesRepository
import com.example.trendy.presentation.use_cases.HomeListMoviesUseCase
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor (
    private val repository: MoviesRepository
) : HomeListMoviesUseCase {
    override suspend fun loadMovies(): Either<ErrorType, List<Movie>> {
        return defaultNetworkCallHandler({repository.loadPopularMovies()})
    }

    override suspend fun loadMoviesFromStorage(): Either<ErrorType, List<Movie>> {
        return defaultDataCallHandler({repository.loadPopularMoviesFromDB()})
    }
}