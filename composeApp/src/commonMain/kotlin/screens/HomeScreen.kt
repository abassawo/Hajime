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
import data.VideoCollection
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.SearchViewModel
import presentation.VideoSetViewState
import presentation.videos.VideoCardTextBelowPreview

data class TopBarAction(val clickAction: () -> Unit)

@Composable
fun MainTopBar(action: () -> Unit)  {
    TopAppBar(title = { Text("Hajime")})
}

@Preview
@Composable
fun App() {
    val destinations = Destination.entries
    val selectedDestination = remember { mutableStateOf(destinations.first()) }
    Scaffold(Modifier.fillMaxSize(), topBar = { MainTopBar {  } }, bottomBar = {
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
        ContinueLearningPage()
    }
}

@Composable
fun ContinueLearningPage() {
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
        Text(text = "Continue Learning", Modifier.padding(16.dp))

        LazyVerticalGrid(GridCells.Fixed(2), Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            items(videoCollection.data) {
                VideoCardTextBelowPreview(it)
            }
        }
    }
}