package com.example.trendy.data.data_sources.local

import com.example.trendy.data.data_sources.models.Movie
import javax.inject.Inject

class AddNewUpComingMoviesDataSource @Inject constructor(
    private val database: MovieDatabase
) {
    suspend fun addNewUpcomingMovies(movies: List<Movie>) {
        movies.forEach { movie -> movie.movieType = 0 }
        database.movieDao().insertAll(movies = movies)
    }
}