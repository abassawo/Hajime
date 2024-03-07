import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.ChannelsViewState
import presentation.VimeoBaseModel
import presentation.channels.VideoListScreen

@Composable
@Preview
fun StaffPick() {
    val baseViewModel = remember { VimeoBaseModel() }
    val viewState = baseViewModel.mutableStateFlow.collectAsState()

    MaterialTheme {
        LaunchedEffect(Unit) {
            val results = baseViewModel.fetchChannel()
            println("Results were $results")
        }
        val channelViewState = viewState.value
        when(channelViewState) {
            is ChannelsViewState.Content -> VideoListScreen(channelViewState.videoCollection)
            is ChannelsViewState.Error -> Text("Error occurred")
            ChannelsViewState.Loading -> CircularProgressIndicator()
        }
    }
}