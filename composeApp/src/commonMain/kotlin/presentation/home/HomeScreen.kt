package presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Video
import presentation.SearchViewModel
import presentation.VideoSetViewState
import presentation.detail.VideoCardTextBelowPreview
import utils.Platform

@Composable
fun HomeScreen(platform: Platform, clickAction: (video: Video) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        val tags = listOf("armbar", "triangle", "guillotine", "ezquiel")
        val viewModel = remember { SearchViewModel(platform, tags) }

        Column(Modifier.fillMaxSize()) {
            when (val result = viewModel.mutableStateFlow.collectAsState().value) {
                is VideoSetViewState.Content -> {

                    Text(text = "Continue Learning", Modifier.padding(16.dp))
                    LazyVerticalGrid(
                        GridCells.Fixed(2),
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                        items(result.videos) {
                            VideoCardTextBelowPreview(it) {
                                clickAction(it)
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}