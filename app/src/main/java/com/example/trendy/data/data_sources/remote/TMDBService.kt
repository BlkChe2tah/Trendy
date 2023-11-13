package com.example.trendy.data.data_sources.remote

import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.data.data_sources.models.MovieDetail
import com.example.trendy.data.data_sources.models.Movies
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBService {

    @GET("/3/movie/upcoming?language=en-US&page=1")
    suspend fun getUpcomingMovies() : Movies

    @GET("/3/movie/popular?language=en-US&page=1")
    suspend fun getPopularMovies() : Movies

    @GET("/3/movie/{movie_id}?language=en-US")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int
    ) : MovieDetail

}