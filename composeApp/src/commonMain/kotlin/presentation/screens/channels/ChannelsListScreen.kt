package presentation.screens.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ChannelsListScreen() {
    val viewModel = remember { ChannelsViewModel() }
    when(val channelViewState = viewModel.mutableStateFlow.collectAsState().value) {
        is ChannelsViewState.Content -> {
            LazyRow(Modifier.fillMaxWidth().background(Color.Black)) {
                items(channelViewState.channels) { channel ->
                    ChannelCard(channel)
                }
            }
        }
        is ChannelsViewState.Error -> Text(channelViewState.message)
        ChannelsViewState.Loading -> CircularProgressIndicator()
    }
}

@Composable
fun ChannelCard(viewEntity: ChannelViewEntity) {
    Card(backgroundColor = Color.DarkGray, modifier = Modifier.padding(16.dp).size(80.dp)) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = viewEntity.name, Modifier.clickable {
                println(viewEntity.uri)
                // show toast
            })
        }
    }
}