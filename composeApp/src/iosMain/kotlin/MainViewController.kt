import androidx.compose.ui.window.ComposeUIViewController
import presentation.screens.App
import utils.CommonPlatform

fun MainViewController() = ComposeUIViewController { App(CommonPlatform()) }