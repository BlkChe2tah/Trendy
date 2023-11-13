package com.example.trendy.data.data_sources.models


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_detail_table")
data class MovieDetail(
    @PrimaryKey
    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    var id: Int = 0,
    @Expose(serialize = true, deserialize = true)
    @SerializedName("overview")
    var overview: String = "",
    @Expose(serialize = true, deserialize = true)
    @SerializedName("poster_path")
    var posterPath: String = "",
    @Expose(serialize = true, deserialize = true)
    @SerializedName("release_date")
    var releaseDate: String = "",
    @Expose(serialize = true, deserialize = true)
    @SerializedName("runtime")
    var runtime: Int = 0,
    @Expose(serialize = true, deserialize = true)
    @SerializedName("status")
    var status: String = "",
    @Expose(serialize = true, deserialize = true)
    @SerializedName("title")
    var title: String = "",
    @Ignore
    @Expose(serialize = false, deserialize = false)
    var isSaved: MutableState<Boolean> = mutableStateOf(false),
)