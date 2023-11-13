package com.example.trendy.domain.use_cases

import arrow.core.Either
import com.example.trendy.common.ErrorType
import com.example.trendy.common.defaultDataCallHandler
import com.example.trendy.data.data_sources.local.FavouriteMoviesDataSource
import com.example.trendy.data.data_sources.models.MovieDetail
import com.example.trendy.presentation.use_cases.FavouriteMoviesUseCase
import javax.inject.Inject

class FavouriteMoviesUseCaseImpl @Inject constructor (
    private val dataSource: FavouriteMoviesDataSource
) : FavouriteMoviesUseCase {

    override suspend fun loadMovies(): Either<ErrorType, List<MovieDetail>> {
        return defaultDataCallHandler({dataSource.loadFavouriteMovies()})
    }
}