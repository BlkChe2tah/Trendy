package com.example.trendy.data.data_sources.local

import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.data.data_sources.models.MovieDetail
import javax.inject.Inject


class FavouriteMoviesDataSource @Inject constructor(
    private val database: MovieDatabase
) {
    suspend fun loadFavouriteMovies() : List<MovieDetail> {
        return database.movieDetailDao().getAllFavouriteMovies()
    }
}