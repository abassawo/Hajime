package screens

import BottomBarItem
import Destination
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.VideoCollection
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.SearchViewModel
import presentation.VideoSetViewState
import presentation.videos.VideoCard

@Preview
@Composable
fun App() {
    val destinations = Destination.entries
    val selectedDestination = remember { mutableStateOf(destinations.first()) }
    Scaffold(Modifier.fillMaxSize(), bottomBar = {
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
                Destination.Home -> HomeScreen()
                Destination.Community -> Text("Community")
                Destination.Account -> Text("Account")
            }
        }


    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen() {
    Column(Modifier.fillMaxSize()) {
//        Image(
//            painterResource(DrawableResource("compose-multiplatform")),
//            null,
//            modifier = Modifier.size(50.dp)
//        )
        TestVideoHomeScreen()
    }
}

@Composable
fun TestVideoHomeScreen() {
    val tags = listOf("armbar", "triangle", "guillotine", "ezquiel")

    val viewModel = remember { SearchViewModel(tags) }

    Column(Modifier.fillMaxSize()) {
        when (val result = viewModel.mutableStateFlow.collectAsState().value) {
            is VideoSetViewState.Content -> VideoResults(result.videoCollection)
            is VideoSetViewState.Error -> Unit
            VideoSetViewState.Loading -> Unit
        }
    }
}

@Composable
fun VideoResults(videoCollection: VideoCollection) {
    Column {
        Box(Modifier.background(Color.Black).fillMaxWidth().height(300.dp)) {
        }
        LazyColumn(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            items(videoCollection.data) {
                VideoCard(it)
                Divider(Modifier.background(Color.Black))
            }
        }
    }
}