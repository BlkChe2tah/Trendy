package com.example.trendy.data.repositories

import com.example.trendy.data.data_sources.MoviesDataSource
import com.example.trendy.data.data_sources.local.AddNewPopularMoviesDataSource
import com.example.trendy.data.data_sources.local.AddNewUpComingMoviesDataSource
import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.domain.repositories.MoviesRepository
import javax.inject.Inject
import javax.inject.Named

class MoviesRepositoryImpl @Inject constructor(
    @Named("TMDB") private val remote: MoviesDataSource,
    @Named("ROOM") private val db: MoviesDataSource,
    private val addUpcomingDatasource: AddNewUpComingMoviesDataSource,
    private val addPopularDatasource: AddNewPopularMoviesDataSource
) : MoviesRepository {

    override suspend fun loadUpComingMovies(): List<Movie> {
        val result = remote.loadUpComingMovies()
        addUpcomingDatasource.addNewUpcomingMovies(result)
        return result
    }

    override suspend fun loadUpComingMoviesFromDB(): List<Movie> {
        return db.loadUpComingMovies()
    }

    override suspend fun loadPopularMovies(): List<Movie> {
        val result = remote.loadPopularMovies()
        addPopularDatasource.addPopularMovies(result)
        return result
    }

    override suspend fun loadPopularMoviesFromDB(): List<Movie> {
        return db.loadPopularMovies()
    }
}