import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.VideoSetViewState
import presentation.StaffPickViewModelImpl
import presentation.videos.VideoListScreen

@Composable
@Preview
fun ChannelResults(channel: String) {
    val viewState = remember { StaffPickViewModelImpl(channel) }.mutableStateFlow.collectAsState()

    MaterialTheme {
        val channelViewState = viewState.value
        when(channelViewState) {
            is VideoSetViewState.Content -> VideoListScreen(channelViewState.videoCollection)
            is VideoSetViewState.Error -> Text("Error occurred")
            VideoSetViewState.Loading -> CircularProgressIndicator()
        }
    }
}