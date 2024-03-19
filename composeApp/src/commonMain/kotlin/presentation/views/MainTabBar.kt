package presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Video
import hajime.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.screens.SearchViewModel
import utils.navigation.NavigationStack


@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainTopBar(searchViewModel: SearchViewModel, navigationStack: NavigationStack<Any>) {
    val isVideoPlayerScreen = navigationStack.lastWithIndex().value == Destination.VideoPlayer
    val video = searchViewModel.selectedVideo
    val backAction = {
        navigationStack.back()
    }
    val backArrowResource = Icons.Default.ArrowBack
    TopAppBar(
        title = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Hajime", Modifier.fillMaxWidth())
            }

        },
        navigationIcon = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (isVideoPlayerScreen) {
                    Icon(
                        backArrowResource,
                        "",
                        modifier = Modifier.padding(0.dp).clickable { backAction() })
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        "",
                        modifier = Modifier.padding(0.dp)
                            .size(32.dp)
                    )
                }
            }
        }, actions = {
            if(isVideoPlayerScreen) {
                video?.let {
                    Icon(
                        Icons.Default.Share,
                        "",
                        modifier = Modifier.padding(0.dp).clickable {
                            searchViewModel.onShareClicked(it)
                        })
                }

            }
        }
    )
}



