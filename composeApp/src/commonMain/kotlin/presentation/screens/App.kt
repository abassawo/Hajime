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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import data.Video
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.detail.VideoPlayerData
import presentation.screens.detail.VideoPlayerScreen
import presentation.screens.explore.ExploreTopicsScreen
import presentation.screens.explore.VideoResultsGrid
import presentation.screens.explore.verticalGradient
import presentation.screens.home.HomeScreen
import presentation.screens.onboarding.OnboardingFlow
import presentation.screens.onboarding.OnboardingViewModel
import presentation.views.BottomBarItem
import presentation.views.Destination
import presentation.views.MainTopBar
import utils.CommonPlatform
import utils.Platform
import utils.navigation.NavigationStack


@Preview
@Composable
fun App(onboardingViewModel: OnboardingViewModel = OnboardingViewModel()) {
    val navigationStack = rememberSaveable(
        saver = listSaver(
            restore = { NavigationStack(*it.toTypedArray()) },
            save = { it.stack },
        )
    ) {
        NavigationStack(Destination.Home)
    }
    if (onboardingViewModel.isFirstRun) {
        onboardingViewModel.isFirstRun = false
        OnboardingFlow(onboardingViewModel)
    } else {
        AppScaffold(CommonPlatform(), navigationStack)
    }
}

@Composable
fun AppScaffold(platform: Platform, navigationStack: NavigationStack<Destination>) {
    val searchViewModel = remember { platform.searchViewModel }
    Scaffold(Modifier.fillMaxSize(),
        topBar = {
            MainTopBar(navigationStack)
        },
        bottomBar = {
            if (navigationStack.lastWithIndex().value != Destination.VideoPlayer) {
                BottomAppBar {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val destinations =
                            listOf(
                                Destination.Home,
                                Destination.Explore,
                                Destination.Favorites,
                                Destination.Community,
                                Destination.Profile
                            )
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
//                        ExploreTopicsScreen(navigationStack)
                        val nextUpTopicForCurrentUser = "armbar"
                        VideoResultsGrid(nextUpTopicForCurrentUser, CommonPlatform(), {
                            searchViewModel.prepareVideoPlayback(it)
                            val destination = Destination.VideoPlayer
                            destination.data = VideoPlayerData(it, searchViewModel.allVideos.toList())
                            navigationStack.push(destination)
                        })
                    }

                    Destination.Community -> Text(
                        "Community", Modifier.fillMaxSize()
                    )

                    Destination.Profile -> Text(
                        "Account"
                    )

                    Destination.VideoPlayer -> VideoPlayerScreen(
                        platform,
                        page.data as VideoPlayerData
                    )

                    Destination.Home -> HomeScreen()
                    Destination.Favorites -> Text(
                        "Inbox"
                    )

                    Destination.VideoResults -> VideoResultsGrid(
                        page.data as? String ?: "",
                        CommonPlatform(),
                        {
                            searchViewModel.prepareVideoPlayback(it)
                            val destination = Destination.VideoPlayer
                            destination.data = VideoPlayerData(it, searchViewModel.allVideos.toList())
                            navigationStack.push(destination)
                        })
                }
            }
        }
    }
}