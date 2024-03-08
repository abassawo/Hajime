import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import presentation.SearchViewModel
import presentation.VideoSetViewState
import presentation.videos.VideoListScreen

@Composable

fun ChannelResults(channel: String) {
    val viewState = remember { SearchViewModel() }.mutableStateFlow.collectAsState()

    MaterialTheme {
        val channelViewState = viewState.value
        when(channelViewState) {
            is VideoSetViewState.Content -> VideoListScreen(channelViewState.videoCollection)
            is VideoSetViewState.Error -> Text("Error occurred")
            VideoSetViewState.Loading -> CircularProgressIndicator()
        }
    }
}