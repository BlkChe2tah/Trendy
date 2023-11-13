package com.example.trendy.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.trendy.data.data_sources.models.Movie

@Composable
fun MovieCard(movie: Movie, onClick:(Int) -> Unit  ,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(270.dp)
            .height(360.dp)
            .padding(8.dp)
            .clickable {
                onClick(movie.id)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

        }
    }
}