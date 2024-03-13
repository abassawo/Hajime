package screens

import BottomBarItem
import Destination
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.SearchViewModel
import presentation.VideoSetViewState
import presentation.videos.VideoCardTextBelowPreview
import utils.Platform

data class TopBarAction(val clickAction: () -> Unit)

@Composable
fun MainTopBar(action: () -> Unit) {
    TopAppBar(title = { Text("Hajime") })
}

@Preview
@Composable
fun App(platform: Platform) {
    val destinations = Destination.entries
    val selectedDestination = remember { mutableStateOf(destinations.first()) }
    Scaffold(Modifier.fillMaxSize(), topBar = { MainTopBar { } }, bottomBar = {
        BottomAppBar {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                destinations.forEach {
                    BottomBarItem(it) { selectedDestination.value = it }
                }
            }
        }
    }) {
        Column(Modifier.fillMaxSize().padding(it)) {
            when (selectedDestination.value) {
                Destination.Home -> HomeScreen(platform)
                Destination.Community -> Text("Community")
                Destination.Account -> Text("Account")
            }
        }
    }
}


@Composable
fun HomeScreen(platform: Platform) {
    Column(Modifier.fillMaxSize()) {
        val tags = listOf("armbar", "triangle", "guillotine", "ezquiel")
        //        val showVideoPlayer = remember { mutableStateOf(false) }
        val viewModel = remember { SearchViewModel(platform, tags) }

        Column(Modifier.fillMaxSize()) {
            when (val result = viewModel.mutableStateFlow.collectAsState().value) {
                is VideoSetViewState.Content -> {
                    //                    if (showVideoPlayer.value) {
                    //                    VideoPlayerScreen(result.videos.first(), result.videos)
                    //                } else {
                    Text(text = "Continue Learning", Modifier.padding(16.dp))
                    LazyVerticalGrid(
                        GridCells.Fixed(2),
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                                                items(result.videos) {
                                                    VideoCardTextBelowPreview(it) {
//                                                        showVideoPlayer.value = true
                                                    }
                                                }
                                        }
                    }


                //                is VideoSetViewState.Error -> Unit
                //                VideoSetViewState.Loading -> Unit
                is VideoSetViewState.Error -> Unit
                VideoSetViewState.Loading -> Unit
            }
        }
    }
}