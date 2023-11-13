package com.example.trendy.data.data_sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendy.data.data_sources.models.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table WHERE movie_type = 0")
    fun loadUpcomingMovies(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE movie_type = 1")
    fun loadPopularMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)


}