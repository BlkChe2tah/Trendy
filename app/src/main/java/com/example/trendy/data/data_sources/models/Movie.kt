package com.example.trendy.data.data_sources.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("poster_path")
    var posterPath: String = "",
    @ColumnInfo(name = "movie_type")
    var movieType: Int = 0
)