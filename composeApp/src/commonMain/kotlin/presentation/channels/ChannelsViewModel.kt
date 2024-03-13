package presentation.channels

import data.ChannelsResponse
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
    data class Content(val channels: List<ChannelViewEntity>) : ChannelsViewState()
    data class Error(val message: String) : ChannelsViewState()
}

class ChannelsViewModel(val vimeoService: VimeoService = VimeoRepository()) {
    val mutableStateFlow : MutableStateFlow<ChannelsViewState> = MutableStateFlow(ChannelsViewState.Loading)
    val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )

    init {
        refresh()
    }
    fun refresh() {
        mutableStateFlow.value = ChannelsViewState.Loading
            coroutineScope.launch {
            runCatching { getChannels() }
                .mapCatching { it.data.map { ChannelViewEntity(it) } }
                .onSuccess { mutableStateFlow.value = ChannelsViewState.Content(it) }
                .onFailure {  mutableStateFlow.value =
                    ChannelsViewState.Error("An error occurred")
                }
        }
    }

    private suspend fun getChannels(): ChannelsResponse {
        // could be used to find other channels
        return vimeoService.getChannels()
    }
}