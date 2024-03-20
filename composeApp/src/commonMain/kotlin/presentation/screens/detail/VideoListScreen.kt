package presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import data.Video
import presentation.screens.SearchViewModel
import presentation.views.VideoPlayer

@Composable
fun VideoPlayerScreen(viewModel: SearchViewModel) {
    val videoCollection = viewModel.allVideos
    var selectedVideo: Video =  remember {  viewModel.selectedVideo ?: videoCollection.first() }

    key(selectedVideo) {
        val playbackUrl = viewModel.streamUrl.value
        if (playbackUrl.isNotEmpty()) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                selectedVideo.streamUrl?.let {
                    VideoPlayer(
                        Modifier.background(Color.Black).fillMaxWidth().height(300.dp),
                        url = it
                    )
                } ?: Text("Error occurred")

                Text(
                    text = selectedVideo.name ?: "",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(
                    text = selectedVideo.description ?: "",
                    maxLines = 4,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                LazyColumn(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    item { Spacer(Modifier.height(16.dp)) }
                    item { Text("Similar videos") }
                    items(videoCollection - selectedVideo) {
                        VideoCardTextNextToPreview(it) {
                            selectedVideo = it
                        }
                        Divider(Modifier.background(Color.Black))
                    }
                }
            }
        }

    }
}

@Composable
fun VideoCardTextNextToPreview(video: Video, clickAction: (video: Video) -> Unit) {
    Row(Modifier.clickable { clickAction(video) }) {
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
            Text(text = video.name ?: "", color = Color.White)
//            Text(text = video.description ?: "", maxLines = 3, color = Color.White)
        }

    }
}
