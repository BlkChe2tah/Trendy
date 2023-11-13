package com.example.trendy.domain.use_cases

import arrow.core.Either
import com.example.trendy.common.ErrorType
import com.example.trendy.common.defaultNetworkCallHandler
import com.example.trendy.data.data_sources.local.MovieSaveStateCheckDataSource
import com.example.trendy.data.data_sources.local.ToggleMovieDetailDataSource
import com.example.trendy.data.data_sources.models.MovieDetail
import com.example.trendy.data.data_sources.remote.TMDBMovieDetailDataSource
import com.example.trendy.data.data_sources.remote.TMDBService
import com.example.trendy.presentation.use_cases.MovieDetailUseCase
import javax.inject.Inject

class MovieDetailUseCaseImpl @Inject constructor (
    private val dataSource: TMDBMovieDetailDataSource,
    private val toggleDataSource: ToggleMovieDetailDataSource,
    private val isSavedCheckDataSource: MovieSaveStateCheckDataSource
) : MovieDetailUseCase {
    override suspend fun loadMovieDetail(id: Int): Either<ErrorType, MovieDetail> {
        return defaultNetworkCallHandler({
            val result = dataSource.loadMovieDetail(id)
            val isSaved = isSavedCheckDataSource.isMovieSaved(result.id)
            result.isSaved.value = isSaved
            result
        })
    }

    override suspend fun save(data: MovieDetail) {
        toggleDataSource.save(data = data)
    }

    override suspend fun delete(data: MovieDetail) {
        toggleDataSource.delete(data = data)
    }
}