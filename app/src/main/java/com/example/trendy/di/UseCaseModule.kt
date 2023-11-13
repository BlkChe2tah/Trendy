package com.example.trendy.di

import com.example.trendy.domain.use_cases.FavouriteMoviesUseCaseImpl
import com.example.trendy.domain.use_cases.MovieDetailUseCaseImpl
import com.example.trendy.domain.use_cases.UpComingMoviesUseCase
import com.example.trendy.domain.use_cases.PopularMoviesUseCase
import com.example.trendy.presentation.use_cases.FavouriteMoviesUseCase
import com.example.trendy.presentation.use_cases.HomeListMoviesUseCase
import com.example.trendy.presentation.use_cases.MovieDetailUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindPopularMoviesUseCase(
        popular: PopularMoviesUseCase
    ) : HomeListMoviesUseCase

    @Binds
    @Singleton
    abstract fun bindUpComingMoviesUseCase(
        upcoming: UpComingMoviesUseCase
    ) : HomeListMoviesUseCase

    @Binds
    @Singleton
    abstract fun bindMovieDetailUseCaseImpl(
        useCase: MovieDetailUseCaseImpl
    ) : MovieDetailUseCase

    @Binds
    @Singleton
    abstract fun bindFavouriteMoviesUseCaseImpl(
        useCase: FavouriteMoviesUseCaseImpl
    ) : FavouriteMoviesUseCase
}