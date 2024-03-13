import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import presentation.SearchViewModel
import presentation.VideoSetViewState
import utils.Platform

@Composable
fun ChannelResults(platform: Platform, channel: String) {
    val viewState =
        remember { SearchViewModel(platform) } // todo - create dedicated viewmodel, pass in Channel
            .mutableStateFlow.collectAsState()

    MaterialTheme {
        val channelViewState = viewState.value
        when (channelViewState) {
            is VideoSetViewState.Content -> Text("Content WIP") // VideoPlayerScreen(channelViewState.videos)
            is VideoSetViewState.Error -> Text("Error occurred")
            VideoSetViewState.Loading -> CircularProgressIndicator()
        }
    }
}