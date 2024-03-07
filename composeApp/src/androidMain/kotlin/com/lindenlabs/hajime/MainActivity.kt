package com.lindenlabs.hajime

import ChannelResults
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import presentation.channels.ChannelsListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(Modifier.fillMaxSize()) {
                ChannelsListScreen()
                ChannelResults("staffpicks")
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    ChannelResults("staffpicks")
}