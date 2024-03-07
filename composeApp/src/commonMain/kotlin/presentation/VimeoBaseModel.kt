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

sealed class ChannelsViewState {
    object Loading : ChannelsViewState()
    data class Content(val videoCollection: VideoCollection) : ChannelsViewState()
    data class Error(val message: String) : ChannelsViewState()
}

class VimeoBaseModel(val vimeoService: VimeoService = VimeoRepository()) {
    val mutableStateFlow : MutableStateFlow<ChannelsViewState> = MutableStateFlow(ChannelsViewState.Loading)
    val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob())

//    fun getAllChannels() {
//        mutableStateFlow.value = ChannelsViewState.Loading
//        coroutineScope.launch {
//            runCatching { getVideoFeed() }
//                .mapCatching { it.data.map { ChannelViewEntity(it) } }
//                .onSuccess { mutableStateFlow.value = ChannelsViewState.Content(it) }
//                .onFailure { mutableStateFlow.value = ChannelsViewState.Error("An error occurred") }
//        }
//    }

    fun fetchChannel(channelName: String = "staffpicks") {
        mutableStateFlow.value = ChannelsViewState.Loading
        coroutineScope.launch {
            runCatching { vimeoService.getVideos(channelName) }
                .mapCatching { it }
                .onSuccess { mutableStateFlow.value = ChannelsViewState.Content(it) }
                .onFailure { mutableStateFlow.value = ChannelsViewState.Error("An error occurred $it") }
        }
    }

    private suspend fun getVideoFeed(): ChannelsResponse {
        return vimeoService.getChannels()
    }
}