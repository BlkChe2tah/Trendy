package com.example.trendy.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.trendy.common.RequestInterceptor
import com.example.trendy.common.connectivity_checker.ConnectivityChecker
import com.example.trendy.common.connectivity_checker.ConnectivityCheckerImpl
import com.example.trendy.common.ApiResponseErrorHandlingInterceptor
import com.example.trendy.common.NetworkConnectionInterceptor
import com.example.trendy.data.data_sources.local.AddNewPopularMoviesDataSource
import com.example.trendy.data.data_sources.local.AddNewUpComingMoviesDataSource
import com.example.trendy.data.data_sources.local.FavouriteMoviesDataSource
import com.example.trendy.data.data_sources.local.MovieDatabase
import com.example.trendy.data.data_sources.local.MovieSaveStateCheckDataSource
import com.example.trendy.data.data_sources.local.ToggleMovieDetailDataSource
import com.example.trendy.data.data_sources.remote.TMDBMovieDetailDataSource
import com.example.trendy.data.data_sources.remote.TMDBService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context,
    ) : ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideTMDBService(
        connectivityChecker: ConnectivityChecker
    ): TMDBService {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(NetworkConnectionInterceptor(connectivityChecker))
            addInterceptor(ApiResponseErrorHandlingInterceptor())
            addInterceptor(RequestInterceptor())
        }
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAddNewUpComingMoviesDataSource(
        movieDatabase: MovieDatabase
    ) : AddNewUpComingMoviesDataSource {
        return AddNewUpComingMoviesDataSource(movieDatabase)
    }

    @Provides
    @Singleton
    fun provideAddNewPopularMoviesDataSource(
        movieDatabase: MovieDatabase
    ) : AddNewPopularMoviesDataSource {
        return AddNewPopularMoviesDataSource(movieDatabase)
    }

    @Provides
    @Singleton
    fun provideToggleMovieDetailDataSource(
        movieDatabase: MovieDatabase
    ) : ToggleMovieDetailDataSource {
        return ToggleMovieDetailDataSource(movieDatabase)
    }

    @Provides
    @Singleton
    fun provideMovieSaveStateCheckDataSource(
        movieDatabase: MovieDatabase
    ) : MovieSaveStateCheckDataSource {
        return MovieSaveStateCheckDataSource(movieDatabase)
    }

    @Provides
    @Singleton
    fun provideFavouriteMoviesDataSource(
        movieDatabase: MovieDatabase
    ) : FavouriteMoviesDataSource {
        return FavouriteMoviesDataSource(movieDatabase)
    }

    @Provides
    @Singleton
    fun provideTMDBMovieDetailDataSource(
        service: TMDBService
    ) : TMDBMovieDetailDataSource {
        return TMDBMovieDetailDataSource(service)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppCommonModule {
    @Binds
    @Singleton
    abstract fun bindConnectivityChecker(
        connectivityChecker: ConnectivityCheckerImpl
    ) : ConnectivityChecker
}