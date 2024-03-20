package presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.detail.VideoPlayerScreen
import presentation.screens.explore.verticalGradient
import presentation.screens.home.HomeScreen
import presentation.screens.onboarding.OnboardingFlow
import presentation.views.BottomBarItem
import presentation.views.Destination
import presentation.views.MainTopBar
import utils.CommonPlatform
import utils.Platform
import utils.navigation.NavigationStack


@Preview
@Composable
fun App(platform: Platform = CommonPlatform()) {
    val searchViewModel = SearchViewModel(platform)
    val navigationStack = rememberSaveable(
        saver = listSaver(
            restore = { NavigationStack(*it.toTypedArray()) },
            save = { it.stack },
            )
    ) {
        NavigationStack(Destination.Home)
    }
    if(searchViewModel.isFirstRun) {
        searchViewModel.isFirstRun = false
        OnboardingFlow(navigationStack)
    } else {
        AppScaffold(searchViewModel, navigationStack)
    }
}

@Composable
fun AppScaffold(searchViewModel: SearchViewModel, navigationStack: NavigationStack<Destination>) {
    Scaffold(Modifier.fillMaxSize(),
             topBar = {
                 MainTopBar(searchViewModel, navigationStack)
                      },
             bottomBar = {
                 if(navigationStack.lastWithIndex().value != Destination.VideoPlayer) {
                     BottomAppBar {
                         Row(
                             horizontalArrangement = Arrangement.SpaceBetween,
                             modifier = Modifier.fillMaxWidth()
                         ) {
                             val destinations =
                                 listOf(Destination.Home, Destination.Explore, Destination.Community)
                             destinations.forEach {
                                 BottomBarItem(it) { destination ->
                                     navigationStack.push(destination)
                                 }
                             }
                         }
                     }
                 }
             }) {
        Box(Modifier.fillMaxSize().background(verticalGradient)) {
            AnimatedContent(targetState = navigationStack.lastWithIndex()) { (_, page) ->
                when (page) {
                    Destination.Explore -> {
                        val topic = searchViewModel.tags.firstOrNull() ?: "armbar"

                    }
                    Destination.Community -> Text(
                        "Community", Modifier.fillMaxSize()
                    )

                    Destination.Account -> Text(
                        "Account")

                    Destination.VideoPlayer -> VideoPlayerScreen(searchViewModel)
                    Destination.Home -> HomeScreen()
                }
            }
        }
    }
}