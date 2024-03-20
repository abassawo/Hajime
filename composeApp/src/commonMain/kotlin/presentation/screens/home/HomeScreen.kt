package presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import data.BeltLevel
import kotlinx.datetime.LocalDateTime
import presentation.views.CurriculumCard
import utils.DateTimeUtil
import utils.Platform

@Composable
fun HomeScreen(platform: Platform) {
    val homeViewModel = HomeViewModel(platform)
    val curriculum = homeViewModel.learningCurriculum
    val currentBeltLevel = BeltLevel.White // todo - retrieve this from KYC data
    LazyColumn(Modifier.fillMaxSize()) {
        items(curriculum.entries.filter { it.key == currentBeltLevel }) { entry ->
            CurriculumCard(entry)
        }
    }
//    Column(
//        Modifier.fillMaxWidth()
////            .padding(16.dp)
//        .background(brush = verticalGradient)
//
//    ) {
//        Spacer(Modifier.height(100.dp))
//        Greeting(Modifier.size(48.dp))
//        Text(text = "Next up")
//        // todo - derive next up from viewmodel
//    }
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
        time.hour in 0 .. 12 -> {
            TimeOfDay.Morning
        }
        else -> TimeOfDay.Evening
    }
}
