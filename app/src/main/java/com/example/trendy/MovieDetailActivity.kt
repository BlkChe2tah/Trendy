package com.example.trendy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.trendy.common.ErrorType.NoConnection.code
import com.example.trendy.common.UiState
import com.example.trendy.data.data_sources.models.MovieDetail
import com.example.trendy.presentation.view_models.HomeViewModel
import com.example.trendy.presentation.view_models.MovieDetailViewModel
import com.example.trendy.ui.common.EmptyLayout
import com.example.trendy.ui.common.ErrorLayout
import com.example.trendy.ui.common.LoadingLayout
import com.example.trendy.ui.theme.TrendyTheme
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MovieDetailActivity: ComponentActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private var movieId = 0
    private var movieData: MovieDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = intent.getIntExtra("movie_id", 0)
        intent.getStringExtra("movie_detail")?.let {
            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
            movieData = gson.fromJson(it, MovieDetail::class.java)
        }
        if (movieId != 0 && viewModel.data.value == null && movieData == null) {
            viewModel.loadMoviesDetail(movieId)
        }
        setContent {
            TrendyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (movieData != null) {
                        DetailView(data = movieData!!, viewModel = viewModel, showBtn = false)
                    } else {
                        ContentFrame(viewModel = viewModel) {
                            viewModel.loadMoviesDetail(movieId = movieId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentLayout(viewModel: MovieDetailViewModel) {
    val data by viewModel.data.collectAsStateWithLifecycle()
    data?.let {
        DetailView(data = it, viewModel = viewModel)
    }
}


@Composable
private fun DetailView(data: MovieDetail, viewModel: MovieDetailViewModel, showBtn: Boolean = true) {
    Column {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${data.posterPath}",
            contentDescription = "",
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .wrapContentHeight()
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .weight(1f)
                )

                if (showBtn) {
                    CheckBtn(isSaved = data.isSaved) {
                        if (data.isSaved.value) {
                            viewModel.delete()
                        } else {
                            viewModel.save()
                        }
                    }
                }

            }
            Row(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .wrapContentHeight()
            ) {
                Text(
                    text = data.releaseDate,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(1f)
                )
                Text(
                    text = data.status,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(1f)
                )
                Text(
                    text = convertMinutesToHHMM(data.runtime),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(1f)
                )
            }
            Text(
                text = data.overview,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .alpha(0.8f)
            )
        }

    }
}

@Composable
private fun CheckBtn(isSaved: MutableState<Boolean>, onClick: () -> Unit) {
    var checkState = rememberSaveable { isSaved }
    IconButton(onClick = onClick,
        modifier = Modifier
            .size(58.dp)
    ) {
        Icon(imageVector = if (checkState.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Favourite Toggle Button")
    }
}

private fun convertMinutesToHHMM(minutes: Int): String {
    val hours = minutes / 60
    val remainderMinutes = minutes % 60
    return String.format("%dhr %dm", hours, remainderMinutes)
}

@Composable
private fun ContentFrame(viewModel: MovieDetailViewModel, onClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState is UiState.Loading) {
        LoadingLayout()
    }
    if (uiState is UiState.Success) {
        ContentLayout(viewModel = viewModel)
    }
    if (uiState is UiState.Error) {
        val error = uiState as UiState.Error
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.alert),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            val code = error.errorCode
            Text(
                text = if (code == 100) "No internet connection! \n Please try again." else code.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (code != 100) return
            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .padding(top = 24.dp)
            ) {
                Text("Retry")
            }
        }
    }
}