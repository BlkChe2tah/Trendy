package com.example.trendy.data.data_sources.local

import com.example.trendy.data.data_sources.models.Movie
import javax.inject.Inject

class AddNewPopularMoviesDataSource @Inject constructor(
    private val database: MovieDatabase
) {
    suspend fun addPopularMovies(movies: List<Movie>) {
        movies.forEach { movie -> movie.movieType = 1 }
        database.movieDao().insertAll(movies = movies)
    }
}