package presentation

import ui.BottomBarItem
import utils.navigation.NavigationStack
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import ui.MainTopBar
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.detail.VideoPlayerScreen
import presentation.home.HomeScreen
import ui.Destination
import utils.Platform

@Preview
@Composable
fun App(platform: Platform) {
    val searchViewModel = SearchViewModel(platform)
    val navigationStack = rememberSaveable(
        saver = listSaver(
            restore = { NavigationStack(*it.toTypedArray()) },
            save = { it.stack },
        )
    ) {
        NavigationStack(Destination.Home)
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            MainTopBar(hasBackButton = navigationStack.lastWithIndex().value == Destination.VideoPlayer) {
                navigationStack.back()
            }
        },
        bottomBar = {
            if(navigationStack.lastWithIndex().value != Destination.VideoPlayer) {
                BottomAppBar {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val destinations =
                            listOf(Destination.Home, Destination.Community, Destination.Account)
                        destinations.forEach {
                            BottomBarItem(it) {
                                navigationStack.push(it)
                            }
                        }
                    }
                }
            }
        }) {
        AnimatedContent(targetState = navigationStack.lastWithIndex()) { (_, page) ->
            when (page) {
                Destination.Home -> HomeScreen(platform) {
                    searchViewModel.selectedVideo = it
                    navigationStack.push(Destination.VideoPlayer)
                }

                Destination.Community -> Text("Community")
                Destination.Account -> Text("Account")
                Destination.VideoPlayer -> VideoPlayerScreen(searchViewModel)
            }
        }
    }
}
