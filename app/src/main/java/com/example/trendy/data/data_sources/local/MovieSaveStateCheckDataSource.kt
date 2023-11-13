package com.example.trendy.data.data_sources.local

import com.example.trendy.data.data_sources.models.Movie
import javax.inject.Inject

class MovieSaveStateCheckDataSource @Inject constructor(
    private val database: MovieDatabase
) {
    suspend fun isMovieSaved(movieId: Int) : Boolean {
        return database.movieDetailDao().checkItemStored(movieId) != 0
    }
}