package presentation

import data.Video
import data.VimeoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import utils.Platform

sealed class VideoSetViewState {
    object Loading : VideoSetViewState()
    data class Content(val videos: List<Video>) : VideoSetViewState()
    data class Error(val message: String) : VideoSetViewState()
}


class SearchViewModel(
    val platform: Platform,
    val tags: List<String> = emptyList() // Currently being used to serve test data, could be potentially a memory cahe in future
) {
    val isLocalDataEnabled: Boolean = true // todo - use a feature flag
    val vimeoService = if (isLocalDataEnabled) platform.localAppDataSource else VimeoRepository()
    val mutableStateFlow: MutableStateFlow<VideoSetViewState> =
        MutableStateFlow(VideoSetViewState.Loading)
    val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )
    val allVideos: MutableList<Video> = mutableListOf()
    var iterator = tags.iterator()

    init {
        refresh()
    }

    fun refresh() {
        if (iterator.hasNext()) {
            val nextTag = iterator.next()
            coroutineScope.launch {
                val videos = getVideos(nextTag)
                allVideos.addAll(videos)
                mutableStateFlow.value = VideoSetViewState.Content(allVideos)
            }
        } else {
            iterator = tags.iterator()
        }
    }

    fun fetchChannel(channelName: String) {
        mutableStateFlow.value = VideoSetViewState.Loading
        coroutineScope.launch {
            runCatching { vimeoService.getVideos(channelName) }
                .mapCatching { it }
                .onSuccess { mutableStateFlow.value = VideoSetViewState.Content(it.data) }
                .onFailure {
                    mutableStateFlow.value = VideoSetViewState.Error("An error occurred $it")
                }
        }
    }

    fun fetchVideos(tag: String) {
        mutableStateFlow.value = VideoSetViewState.Loading
        coroutineScope.launch {
            runCatching {
                vimeoService.searchVideos(tag)
            }
                .onSuccess { mutableStateFlow.value = VideoSetViewState.Content(it.data) }
                .onFailure {
                    mutableStateFlow.value = VideoSetViewState.Error("An error occurred $it")
                }
        }
    }

    suspend fun getVideos(vararg tags: String): List<Video> {
        return buildList<Video> {
            tags.forEach {
                val result = vimeoService.searchVideos(it).data
                addAll(result)
            }
        }
    }
}