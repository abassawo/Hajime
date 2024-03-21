package presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.BeltLevel
import presentation.screens.home.CurriculumModel

@Composable
fun CurriculumCard(userLevel: BeltLevel, entry: Map.Entry<BeltLevel, List<CurriculumModel>>) {
    val beltLevel = entry.key
    Card(Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)) {
        val textColor = when(beltLevel) {
           BeltLevel.White -> Color.Black
           else -> Color.White
        }
        Box(Modifier.fillMaxSize().background(beltLevel.color), contentAlignment = Alignment.Center) {
            Column(Modifier.fillMaxSize()) {
                Text(text = beltLevel.name, color = textColor, textAlign = TextAlign.Center)
            // todo - show preview snippet below
//                if(beltLevel == userLevel) {
//                    Spacer(Modifier.padding(vertical = 16.dp))
//                    //todo - get curriculum lesson
//                    val topicsForCurrentBeltLevel: List<CurriculumModel> = entry.value
//                    val nextTopic = topicsForCurrentBeltLevel?.firstOrNull()
//                    nextTopic?.let {
//                        Card(Modifier.fillMaxWidth()) {
//                            Text(text = nextTopic, Modifier.clickable {  })
//                        }
//                    }
//                }
            }
        }
    }
}

@Composable
fun ResumeLearningCard(beltLevel: BeltLevel) {

}

