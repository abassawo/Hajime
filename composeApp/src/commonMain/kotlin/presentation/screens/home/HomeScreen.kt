package presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import presentation.screens.explore.verticalGradient
import presentation.views.CurriculumCard
import utils.DateTimeUtil

@Composable
fun HomeScreen() {
    val homeViewModel = HomeViewModel()
    val curriculum = homeViewModel.learningCurriculum
    LazyColumn(Modifier.fillMaxSize()) {
        items(curriculum.entries.toList()) { entry ->
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
