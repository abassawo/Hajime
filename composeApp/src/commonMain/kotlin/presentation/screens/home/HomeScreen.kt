package presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.BeltLevel
import kotlinx.datetime.LocalDateTime
import presentation.screens.detail.VideoCardLoading
import presentation.screens.detail.VideoPlayerData
import presentation.views.CurriculumCard
import presentation.views.Destination
import utils.DateTimeUtil
import utils.Platform
import utils.navigation.NavigationStack

@Composable
fun HomeScreen(platform: Platform, navigationStack: NavigationStack<Destination>) {
    val homeViewModel = platform.homeViewModel
    val curriculum = homeViewModel.learningCurriculum
    val currentBeltLevel = BeltLevel.White() // todo - retrieve this from KYC data


    LaunchedEffect(currentBeltLevel) {
        val model = curriculum[currentBeltLevel]?.firstOrNull() ?: "history of jiujitsu"
        homeViewModel.fetchVideos(model)
    }

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Column(
                Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(100.dp))
                Greeting(Modifier.fillMaxHeight().height(48.dp))
                Text(
                    text = "Continue Learning for Belt level: ${currentBeltLevel.name}!",
                    color = Color.White
                )

                val viewState = homeViewModel.mutableVideoState
                when (val value = viewState.value) {
                    is HomeViewState.Content -> {
                        VideoCardLoading(value.resumableVideo, Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                            val destination = Destination.VideoPlayer
                            //                            searchViewModel.prepareVideoPlayback(it)
                            destination.data = VideoPlayerData(it, value.relatedVideos - it)
                            navigationStack.push(Destination.VideoPlayer)
                        }
                    }
                    is HomeViewState.Loading -> CircularProgressIndicator(Modifier.size(16.dp))
                    is HomeViewState.Error -> Text(text = "An error occurred ${value.throwable.cause}" )
                }
            }
        }
        item {
            Text("Review all levels", Modifier.padding(16.dp))
        }
        items(curriculum.entries.toList()) { entry ->
            CurriculumCard(currentBeltLevel, entry)
        }
    }
}

enum class TimeOfDay {
    Morning, Afternoon, Evening
}

@Composable
fun Greeting(modifier: Modifier) {
    val greeting = getTimeOfDay(DateTimeUtil.now())
    Text(text = "Good ${greeting.name.toLowerCase()}", color = Color.White, modifier = modifier)
}

fun getTimeOfDay(time: LocalDateTime): TimeOfDay {
    return when {
        time.hour in 0..12 -> {
            TimeOfDay.Morning
        }

        else -> TimeOfDay.Evening
    }
}
