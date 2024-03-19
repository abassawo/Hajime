package presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.BeltLevel
import presentation.screens.home.CurriculumModel

@Composable
fun CurriculumCard(entry: Map.Entry<BeltLevel, List<CurriculumModel>>) {
    Card(Modifier.fillMaxWidth().height(100.dp)) {
        Column {
            Text(text = entry.key.name)

            LazyColumn {
                items(entry.value) { topic ->
                    Text(text = topic)
                }
            }
        }
    }
}