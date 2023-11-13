package com.example.trendy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.trendy.common.connectivity_checker.ConnectivityChecker
import com.example.trendy.common.UiState
import com.example.trendy.data.data_sources.models.Movie
import com.example.trendy.presentation.view_models.HomeViewModel
import com.example.trendy.presentation.view_models.isMoviesEmpty
import com.example.trendy.ui.common.EmptyLayout
import com.example.trendy.ui.common.ErrorLayout
import com.example.trendy.ui.common.LoadingLayout
import com.example.trendy.ui.home.MovieCard
import com.example.trendy.ui.theme.TrendyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()
    @Inject lateinit var connectivityChecker: ConnectivityChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()

        setContent {
            TrendyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mContext = LocalContext.current
                    Column {
                        ContentFrame(
                            viewModel = viewModel,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Button(
                            onClick = {
                                val intent = Intent(mContext, FavouriteActivity::class.java)
                                mContext.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(text = "Your Favourite Movies")
                        }
                    }
                }
            }
        }
    }

    private fun loadData() {
        val movies = viewModel.movies.value
        if (movies == null || movies.isMoviesEmpty()) {
            viewModel.loadMovies(connectivityChecker.isOnLine())
        }
    }
}

@Composable
private fun ContentFrame(viewModel: HomeViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(modifier = modifier) {
        if (uiState is UiState.Loading) {
            LoadingLayout()
        }
        if (uiState is UiState.Success) {
            ContentLayout(viewModel = viewModel)
        }
        if (uiState is UiState.Empty) {
            EmptyLayout(message = "Empty", icon = R.drawable.movie_projector)
        }
        if (uiState is UiState.Error) {
            val error = uiState as UiState.Error
            ErrorLayout(message = error.errorCode.toString())
        }
    }
}

@Composable
private fun ContentLayout(viewModel: HomeViewModel) {
    val movies by viewModel.movies.collectAsStateWithLifecycle()
    LazyColumn {
        item {
            MovieListView(title = "Upcoming Movies", movies = movies?.upcoming)
        }
        item {
            MovieListView(title = "Popular Movies", movies = movies?.popular)
        }
    }
}

@Composable
private fun MovieListView(title: String, movies: List<Movie>?, modifier: Modifier = Modifier) {
    if (movies?.isEmpty() == true) return
    val mContext = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            movies?.let {
                items(movies) {
                    MovieCard(movie = it, { movieId ->
                        val intent = Intent(mContext, MovieDetailActivity::class.java)
                        intent.putExtra("movie_id", movieId)
                        mContext.startActivity(intent)
                    })
                }
            }
        }
    }
}
