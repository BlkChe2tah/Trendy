package com.example.trendy.di

import com.example.trendy.data.data_sources.MoviesDataSource
import com.example.trendy.data.data_sources.local.RoomMoviesDataSource
import com.example.trendy.data.data_sources.remote.TMDBMoviesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    @Named("TMDB")
    abstract fun bindTMDBMoviesDataSource(
        dataSource: TMDBMoviesDataSource
    ) : MoviesDataSource

    @Binds
    @Singleton
    @Named("ROOM")
    abstract fun bindRoomMoviesDataSource(
        dataSource: RoomMoviesDataSource
    ) : MoviesDataSource
}