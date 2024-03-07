package presentation

import data.ChannelsResponse
import data.VideoCollection
import data.VimeoRepository
import data.VimeoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class VideoSetViewState {
    object Loading : VideoSetViewState()
    data class Content(val videoCollection: VideoCollection) : VideoSetViewState()
    data class Error(val message: String) : VideoSetViewState()
}

interface ChannelFetchViewModel {
    val path: String

    val mutableStateFlow : MutableStateFlow<VideoSetViewState>
    fun fetchChannel(channelName: String = path)
}

class StaffPickViewModelImpl(override val path: String = "staffpicks", val vimeoService: VimeoService = VimeoRepository()) : ChannelFetchViewModel {
    override val mutableStateFlow : MutableStateFlow<VideoSetViewState> = MutableStateFlow(VideoSetViewState.Loading)
    val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob())

    init {
        fetchChannel(path)
    }

    override fun fetchChannel(channelName: String) {
        mutableStateFlow.value = VideoSetViewState.Loading
        coroutineScope.launch {
            runCatching { vimeoService.getVideos(channelName) }
                .mapCatching { it }
                .onSuccess { mutableStateFlow.value = VideoSetViewState.Content(it) }
                .onFailure { mutableStateFlow.value = VideoSetViewState.Error("An error occurred $it") }
        }
    }


}