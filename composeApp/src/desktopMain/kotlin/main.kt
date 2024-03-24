import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.ChannelsResponse
import data.VimeoRepository
import data.VimeoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import presentation.screens.VideoSetViewState
import presentation.screens.App
import presentation.views.Destination
import utils.CommonPlatform
import utils.navigation.NavigationStack

fun  main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Hajime") {
        val navigationStack = rememberSaveable(
            saver = listSaver(
                restore = { NavigationStack(*it.toTypedArray()) },
                save = { it.stack },
                )
        ) {
            NavigationStack(Destination.Home)
        }
        App(CommonPlatform(navigationStack, coroutineScope = CoroutineScope(Dispatchers.Main)))
    }
}