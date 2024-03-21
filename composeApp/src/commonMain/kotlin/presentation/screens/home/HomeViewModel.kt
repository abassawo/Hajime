package presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.BeltLevel
import data.Video
import data.VimeoRepository
import data.VimeoService
import kotlinx.coroutines.launch
import utils.Platform

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Content(val resumableVideo: Video?, val relatedVideos: List<Video>) : HomeViewState()

    data class Error(val throwable: Throwable) : HomeViewState()
}

class HomeViewModel(val platform: Platform, curriculumMaker: CurriculumMaker = CurriculumMaker()) {
    val isLocalDataEnabled: Boolean = false // todo - use a feature flag
    val vimeoService = if (isLocalDataEnabled) platform.localAppDataSource else VimeoRepository()
    val learningCurriculum: Map<BeltLevel, List<CurriculumModel>> = curriculumMaker.makeCurriculum()
    val mutableVideoState: MutableState<HomeViewState> = mutableStateOf(HomeViewState.Loading)
    val allVideoState: MutableState<List<Video>> = mutableStateOf(emptyList())
    val coroutineScope = platform.coroutineScope

    init {
        val beltLevel = BeltLevel.White // todo - get from KYC
        val nextTopic = learningCurriculum[beltLevel]?.firstOrNull()
            ?: "history of jiujitsu" // todo - keep track of the index

        fetchVideos(nextTopic)
    }

    fun fetchVideos(model: CurriculumModel) {
        println("Fetching videos for $model")
        coroutineScope.launch {
            runCatching { vimeoService.searchVideos(model) }
                .mapCatching { it.data.mapPlayable(vimeoService) }
                .onSuccess {
                    allVideoState.value = it
                    if(it.isNotEmpty()) {
                        mutableVideoState.value = HomeViewState.Content(it.firstOrNull(), it)
                    }
                }
                .onFailure {
                    println("An error occurred getting the home screen $it")
                    mutableVideoState.value = HomeViewState.Error(it)
                }
        }
    }
}

suspend fun MutableList<Video>.mapPlayable(
    vimeoService: VimeoService,
    removeUnPlayable: Boolean = true
): List<Video> {
    val sanitizedList = buildList {
        (this@mapPlayable).filter {
            if (removeUnPlayable) it.privacy?.download != false else true }
            .map { video ->
//            if (video.playerEmbedUrl != null) {
//                video.streamUrl = video.playerEmbedUrl
//            } else {
                val id = video.uri?.replace("[^0-9]".toRegex(), "")
                val restUrl = "https://player.vimeo.com/video/$id/config"
                val mediaResponse = vimeoService.streamVideoFromUrl(restUrl)
                video.streamUrl = mediaResponse
                    .request?.files?.progressive?.firstOrNull()?.url ?: ""

                this.add(video)
            }
    }
    return sanitizedList
}