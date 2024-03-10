import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import presentation.SearchViewModel
import presentation.VideoSetViewState

@Composable

fun ChannelResults(channel: String) {
    val viewState = remember { SearchViewModel() }.mutableStateFlow.collectAsState()

    MaterialTheme {
        val channelViewState = viewState.value
        when(channelViewState) {
            is VideoSetViewState.Content -> Text("Content WIP") // VideoPlayerScreen(channelViewState.videos)
            is VideoSetViewState.Error -> Text("Error occurred")
            VideoSetViewState.Loading -> CircularProgressIndicator()
        }
    }
}