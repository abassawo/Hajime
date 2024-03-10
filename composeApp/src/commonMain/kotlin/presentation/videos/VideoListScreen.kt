package presentation.videos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import data.Video
import data.VideoCollection

@Composable
fun VideoListScreen(videoCollection: VideoCollection) {
    Column {
        Box(Modifier.background(Color.Black).fillMaxWidth().height(300.dp)) {
        }
        LazyColumn(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            items(videoCollection.data) {
                VideoCardTextNextToPreview(it)
                Divider(Modifier.background(Color.Black))
            }
        }
    }
}

@Composable
fun VideoCardTextNextToPreview(video: Video) {
    Row {
        AsyncImage(model = video.pictures.baseLink, "", Modifier.size(100.dp))

        Column(Modifier.padding(16.dp)) {
            Text(text = video.name ?: "")
            Text(text = video.description ?: "", maxLines = 3)
        }

    }
}

@Composable
fun VideoCardTextBelowPreview(video: Video) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = video.pictures.baseLink, "", Modifier.size(100.dp))

        Column(Modifier.padding(16.dp)) {
            Text(text = video.name ?: "")
            Text(text = video.description ?: "", maxLines = 3)
        }

    }
}