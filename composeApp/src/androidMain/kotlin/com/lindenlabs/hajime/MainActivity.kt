package com.lindenlabs.hajime

import presentation.channels.ChannelResults
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import presentation.App
import utils.AndroidPlatform

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(platform = AndroidPlatform(LocalContext.current))
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    ChannelResults(AndroidPlatform(LocalContext.current), "")
}