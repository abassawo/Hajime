import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.ChannelsResponse
import data.VimeoRepository
import data.VimeoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import presentation.VideoSetViewState
import screens.App
import utils.CommonPlatform

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Hajime") {
        App(CommonPlatform())
    }
}

class StaffPickViewModelImpl(val vimeoService: VimeoService = VimeoRepository()) {
    val mutableStateFlow : MutableStateFlow<VideoSetViewState> = MutableStateFlow(
        VideoSetViewState.Loading)
    val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )
     val path: String
        get() =  "staffpicks"

//     fun fetchChannel(channelName: String) {
//        mutableStateFlow.value = VideoSetViewState.Loading
//        coroutineScope.launch {
//            runCatching { vimeoService.getVideos(channelName) }
//                .mapCatching { it }
//                .onSuccess { mutableStateFlow.value = VideoSetViewState.Content(it) }
//                .onFailure { mutableStateFlow.value = VideoSetViewState.Error("An error occurred $it") }
//        }
//    }

    private suspend fun getVideoFeed(): ChannelsResponse {
        // could be used to find other channels
        return vimeoService.getChannels()
    }
}