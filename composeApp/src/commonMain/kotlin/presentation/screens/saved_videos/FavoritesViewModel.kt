package presentation.screens.saved_videos

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.LocalDataStore
import data.Video
import kotlinx.coroutines.launch
import utils.Platform

class FavoritesViewModel(val platform: Platform) {

    private val localDataStore = LocalDataStore()
    val videos: MutableState<List<Video>> = mutableStateOf(emptyList())

    fun refresh() {
        platform.coroutineScope.launch {
            videos.value = platform.searchViewModel.allVideos.firstOrNull()?.let { listOf(it) } ?: emptyList()
        }
    }

}