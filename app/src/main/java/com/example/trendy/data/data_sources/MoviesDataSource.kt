package com.example.trendy.data.data_sources

import com.example.trendy.data.data_sources.models.Movie

interface MoviesDataSource {
    suspend fun loadUpComingMovies() : List<Movie>
    suspend fun loadPopularMovies() : List<Movie>
}