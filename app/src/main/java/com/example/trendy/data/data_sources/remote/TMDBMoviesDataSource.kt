package com.example.trendy.data.data_sources.remote

import com.example.trendy.data.data_sources.MoviesDataSource
import com.example.trendy.data.data_sources.models.Movie
import javax.inject.Inject

class TMDBMoviesDataSource @Inject constructor(
    private val service: TMDBService
) : MoviesDataSource {
    override suspend fun loadUpComingMovies(): List<Movie> {
        return service.getUpcomingMovies().results
    }

    override suspend fun loadPopularMovies(): List<Movie> {
        return service.getPopularMovies().results
    }
}