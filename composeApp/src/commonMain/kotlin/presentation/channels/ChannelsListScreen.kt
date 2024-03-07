package presentation.channels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.ChannelViewEntity

@Composable
fun ChannelsListScreen() {
    val viewModel = remember { ChannelsViewModel() }
    when(val channelViewState = viewModel.mutableStateFlow.collectAsState().value) {
        is ChannelsViewState.Content -> {
            LazyRow(Modifier.fillMaxWidth()) {
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
        Text(text = viewEntity.name)
    }
}