package presentation.channels

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import data.Video
import data.VideoCollection
import presentation.ChannelViewEntity

@Composable
fun ChannelsListScreen(videoCollection: VideoCollection) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(videoCollection.data) {
            VideoCard(it)
        }
    }
}

@Composable
fun VideoCard(video: Video) {
    Text(text = video.description)
}

@Composable
fun ChannelCard(viewEntity: ChannelViewEntity) {
    Card(backgroundColor = Color.DarkGray) {
        Text(text = viewEntity.name)
    }
}