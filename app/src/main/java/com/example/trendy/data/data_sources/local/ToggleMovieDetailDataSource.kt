package com.example.trendy.data.data_sources.local

import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.data.data_sources.models.MovieDetail

class ToggleMovieDetailDataSource(
    private val database: MovieDatabase
) {
    suspend fun save(data: MovieDetail) {
        database.movieDetailDao().insert(data)
    }

    suspend fun delete(data: MovieDetail) {
        database.movieDetailDao().delete(data)
    }
}