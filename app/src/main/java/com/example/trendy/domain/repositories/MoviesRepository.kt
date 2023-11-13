package com.example.trendy.domain.repositories

import com.example.trendy.data.data_sources.models.Movie

interface MoviesRepository {
    suspend fun loadUpComingMovies() : List<Movie>
    suspend fun loadUpComingMoviesFromDB() : List<Movie>
    suspend fun loadPopularMovies() : List<Movie>
    suspend fun loadPopularMoviesFromDB() : List<Movie>
}