package presentation

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


class SearchViewModel(
    val tags: List<String> = emptyList(),
    val vimeoService: VimeoService = VimeoRepository()
) {
    val mutableStateFlow: MutableStateFlow<VideoSetViewState> =
        MutableStateFlow(VideoSetViewState.Loading)
    val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )

    init {
        tags.firstOrNull()?.let {
            fetchVideos(it)
        }
    }

    fun fetchChannel(channelName: String) {
        mutableStateFlow.value = VideoSetViewState.Loading
        coroutineScope.launch {
            runCatching { vimeoService.getVideos(channelName) }
                .mapCatching { it }
                .onSuccess { mutableStateFlow.value = VideoSetViewState.Content(it) }
                .onFailure {
                    mutableStateFlow.value = VideoSetViewState.Error("An error occurred $it")
                }
        }
    }

    fun fetchVideos(tag: String) {
        mutableStateFlow.value = VideoSetViewState.Loading
        coroutineScope.launch {
            runCatching { vimeoService.searchVideos(tag) }
                .onSuccess { mutableStateFlow.value = VideoSetViewState.Content(it) }
                .onFailure {
                    mutableStateFlow.value = VideoSetViewState.Error("An error occurred $it")
                }
        }
    }
}