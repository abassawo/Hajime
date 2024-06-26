package presentation.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.Video
import presentation.screens.SearchViewModel
import presentation.screens.VideoSetViewState
import presentation.screens.detail.VideoCardTextBelowPreview
import presentation.screens.detail.VideoPlayerData
import presentation.screens.home.HomeViewModel
import presentation.views.Destination
import utils.Platform
import utils.navigation.NavigationStack

val verticalGradient = Brush.verticalGradient(
    startY = 0f,
    tileMode = TileMode.Repeated,
    colors = listOf(
        Color.Magenta.copy(alpha = 1f),
        Color.Magenta.copy(alpha = 0.7f),
        Color.Magenta.copy(alpha = 0.5f),
        Color.Blue.copy(alpha = 0.5f),
        Color.Blue.copy(alpha = 0.4f),
        Color.Blue.copy(alpha = 0.2f)
    )
)

@Composable
fun ExploreTopicsScreen(platform: Platform, navigationStack: NavigationStack<Destination>) {
    val homeViewModel = HomeViewModel(platform)
    val curriculum = homeViewModel.learningCurriculum

    val allTopics = curriculum.values.toList().flatten()
    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            Modifier.fillMaxSize().padding(bottom = 48.dp)
        ) {
            items(allTopics) { curriculum ->
                val action = {
                    val videoResultsDestination = Destination.VideoResults
                    videoResultsDestination.data = curriculum
                    navigationStack.push(videoResultsDestination)
                }
                Card(Modifier.size(100.dp).padding(16.dp).clickable { action() }) {
                    Box(Modifier.fillMaxSize().clickable { action() }, contentAlignment = Alignment.Center) {
                        Text(
                            text = curriculum,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentSize().clickable {
                                action()
                            })
                    }
                }
            }
        }
//        Spacer(Modifier.height(16.dp))
    }
}


@Composable
fun VideoResultsGrid(
    topic: String,
    platform: Platform,
    navigationStack: NavigationStack<Destination>
) {
    val viewModel = remember { SearchViewModel(platform) }
    LaunchedEffect(topic) {
        viewModel.fetchVideos(topic)
    }

    val viewState = viewModel.mutableStateFlow.collectAsState()

    when (viewState.value) {
        is VideoSetViewState.Content -> {
            Column(
                Modifier.fillMaxSize()
                    .background(brush = verticalGradient)

            ) {
                var offset = remember { mutableStateOf(0f) }
                Box(
                    modifier = Modifier.fillMaxWidth().scrollable(
                        orientation = Orientation.Vertical,
                        state = rememberScrollableState { delta ->
                            offset.value += delta
                            delta
                        })
                ) {
                    Spacer(Modifier.height(200.dp))
                    when (val result = viewModel.mutableStateFlow.collectAsState().value) {
                        is VideoSetViewState.Content -> {

                            LazyVerticalGrid(
                                GridCells.Fixed(2),
                                Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                            ) {
                                items(result.videos) {
                                    VideoCardTextBelowPreview(it) {
                                        val destination = Destination.VideoPlayer
                                        viewModel.prepareVideoPlayback(it)
                                        destination.data = VideoPlayerData(it, result.videos - it)
                                        navigationStack.push(Destination.VideoPlayer)
                                    }
                                }
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }

        is VideoSetViewState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error")
        }
        VideoSetViewState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(Modifier.size(16.dp))
        }
    }
}

