package presentation.videos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun VideoPlayerScreen(selectedVideo: Video, videoCollection: List<Video>) {
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.background(Color.Black).fillMaxWidth().height(300.dp)) {
            // todo - play video
        }
        LazyColumn(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            items(videoCollection) {
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
fun VideoCardTextBelowPreview(video: Video, clickAction: (video: Video) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = video.pictures.baseLink,
            "",
            Modifier.clickable { clickAction(video) }.size(100.dp)
        )

        Column(Modifier.padding(16.dp).clickable { clickAction(video) }) {
            Text(text = video.name ?: "")
            Text(text = video.description ?: "", maxLines = 3)
        }

    }
}