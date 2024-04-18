package presentation.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.Video
import data.VideoStreamResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import utils.Platform

sealed class VideoSetViewState {
    object Loading : VideoSetViewState()
    data class Content(val videos: List<Video>) : VideoSetViewState()
    data class Error(val message: String) : VideoSetViewState()
}


class SearchViewModel(val platform: Platform) {
    var selectedVideo: Video? = null
    val vimeoService = platform.appDataSource
    val mutableStateFlow: MutableStateFlow<VideoSetViewState> =
        MutableStateFlow(VideoSetViewState.Loading)
    val coroutineScope = platform.coroutineScope
    val streamUrl: MutableState<String> = mutableStateOf("")
    val allVideos: MutableSet<Video> = mutableSetOf()
    fun fetchVideos(tag: String) {
        mutableStateFlow.value = VideoSetViewState.Loading
        coroutineScope.launch(context = Dispatchers.IO) {
            runCatching {
                vimeoService.searchVideos(tag)
            }
                .onSuccess {
                    mutableStateFlow.value = VideoSetViewState.Content(it.data)
                    allVideos.addAll(it.data)
                }
                .onFailure {
                    mutableStateFlow.value = VideoSetViewState.Error("An error occurred $it")
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