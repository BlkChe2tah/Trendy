package com.example.trendy.data.data_sources.local

import com.example.trendy.data.data_sources.MoviesDataSource
import com.example.trendy.data.data_sources.models.Movie
import javax.inject.Inject

class RoomMoviesDataSource @Inject constructor(
    private val database: MovieDatabase
) : MoviesDataSource {
    override suspend fun loadUpComingMovies(): List<Movie> {
        return database.movieDao().loadUpcomingMovies()
    }

    override suspend fun loadPopularMovies(): List<Movie> {
        return database.movieDao().loadPopularMovies()
    }
}