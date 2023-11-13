package com.example.trendy.data.data_sources.remote

import com.example.trendy.data.data_sources.models.MovieDetail
import javax.inject.Inject

class TMDBMovieDetailDataSource @Inject constructor(
    private val service: TMDBService
)  {
    suspend fun loadMovieDetail(id: Int): MovieDetail {
        return service.getMovieDetail(id)
    }
}