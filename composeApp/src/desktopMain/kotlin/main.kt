import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import presentation.screens.App
import utils.CommonPlatform

fun  main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Hajime") {
        App(CommonPlatform(coroutineScope = CoroutineScope(Dispatchers.Main)))
    }
}