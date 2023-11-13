package com.example.trendy.data.data_sources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.data.data_sources.models.MovieDetail

@Dao
interface MovieDetailDao {

    @Query("SELECT count(*) FROM movie_detail_table WHERE id = :id")
    suspend fun checkItemStored(id: Int): Int

    @Query("SELECT * FROM movie_detail_table")
    suspend fun getAllFavouriteMovies(): List<MovieDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: MovieDetail)

    @Delete
    suspend fun delete(data: MovieDetail)

}