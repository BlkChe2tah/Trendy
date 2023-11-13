package com.example.trendy.presentation.use_cases

import arrow.core.Either
import com.example.trendy.common.ErrorType
import com.example.trendy.data.data_sources.models.MovieDetail

interface MovieDetailUseCase {
    suspend fun loadMovieDetail(id: Int) : Either<ErrorType, MovieDetail>
    suspend fun save(data: MovieDetail)
    suspend fun delete(data: MovieDetail)
}