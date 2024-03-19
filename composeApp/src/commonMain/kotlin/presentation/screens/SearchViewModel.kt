package presentation.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.Video
import data.VideoStreamResponse
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
    var selectedVideo: Video? = null
    val isLocalDataEnabled: Boolean = false // todo - use a feature flag
    val vimeoService = if (isLocalDataEnabled) platform.localAppDataSource else VimeoRepository()
    val mutableStateFlow: MutableStateFlow<VideoSetViewState> =
        MutableStateFlow(VideoSetViewState.Loading)

    val streamUrl: MutableState<String> = mutableStateOf("")
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
//                result.map { video ->
//                    val id = video.uri?.replace("[^0-9]".toRegex(), "")
//                    val restUrl = "https://player.vimeo.com/video/$id/config"
//                    val mediaResponse = streamVideoFromUrl(restUrl)
//                    video.streamUrl =  mediaResponse
//                        .request?.files?.progressive?.firstOrNull()?.url ?: ""
//                }
                addAll(result)
            }
        }
    }

    suspend fun streamVideoFromUrl(restUrl: String): VideoStreamResponse =
        vimeoService.streamVideoFromUrl(restUrl)

    fun prepareVideoPlayback(video: Video, emit: Boolean = false) {
        selectedVideo = video
        video.streamUrl?.let {
            streamUrl.value = it
        } ?: coroutineScope.launch {
                val id = video.uri?.replace("[^0-9]".toRegex(), "")
                val restUrl = "https://player.vimeo.com/video/$id/config"
                val mediaResponse = streamVideoFromUrl(restUrl)
                video.streamUrl = mediaResponse
                    .request?.files?.progressive?.firstOrNull()?.url ?: ""
            if(emit) {
                streamUrl.value = mediaResponse
                    .request?.files?.progressive?.firstOrNull()?.url ?: ""
            }
            }

        }

    fun onShareClicked(it: Video) {
        // emit view event for sharing the video
    }

}