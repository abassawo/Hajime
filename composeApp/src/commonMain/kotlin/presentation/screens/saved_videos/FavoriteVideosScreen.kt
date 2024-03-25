package presentation.screens.saved_videos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.screens.detail.VideoCardTextNextToPreview
import utils.Platform

@Composable
fun FavoriteVideoScreen(platform: Platform) {
    val videos = platform.favoriteViewModel.videos.value

    LaunchedEffect(Unit) {
        platform.favoriteViewModel.refresh()
    }

    if(videos.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No saved videos")
        }
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            item{
                Text("Saved videos", modifier = Modifier.padding(horizontal = 16.dp))
            }
            items(videos) {
                VideoCardTextNextToPreview(it) {

                }
            }
        }
    }
}
