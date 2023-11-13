package com.example.trendy.di

import com.example.trendy.data.repositories.MoviesRepositoryImpl
import com.example.trendy.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMoviesRepositoryImpl(
        repository: MoviesRepositoryImpl
    ) : MoviesRepository
}