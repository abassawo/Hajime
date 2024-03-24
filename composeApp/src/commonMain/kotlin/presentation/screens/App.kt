package presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.detail.VideoPlayerData
import presentation.screens.detail.VideoPlayerScreen
import presentation.screens.explore.ExploreTopicsScreen
import presentation.screens.explore.VideoResultsGrid
import presentation.screens.explore.verticalGradient
import presentation.screens.home.HomeScreen
import presentation.screens.onboarding.OnboardingFlow
import presentation.screens.onboarding.OnboardingViewModel
import presentation.views.BottomAppBarImpl
import presentation.views.Destination
import presentation.views.MainTopBar
import utils.Platform

@Preview
@Composable
fun App(platform: Platform) {
    AppScaffold(platform)

    LaunchedEffect(Unit) {
        val onboardingViewModel: OnboardingViewModel = platform.onboardingViewModel
        if (onboardingViewModel.isKycComplete().not()) {
            platform.navigationStack.push(Destination.Kyc)
        }
    }
}

@Composable
fun AppScaffold(platform: Platform) {
    val navigationStack = remember { platform.navigationStack }

    Scaffold(Modifier.fillMaxSize(),
        topBar = {
            MainTopBar(navigationStack)
        },
        bottomBar = {
           BottomAppBarImpl(navigationStack)
        }) {
        Box(Modifier.fillMaxSize().background(verticalGradient)) {
            AnimatedContent(targetState = navigationStack.lastWithIndex()) { (_, page) ->
                when (page) {
                    Destination.Kyc -> {
                        OnboardingFlow(platform) {
                            platform.onboardingViewModel.submitKyc()
                            platform.navigationStack.push(Destination.Home)
                        }
                    }
                    Destination.Explore -> {
                        ExploreTopicsScreen(platform)
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

                    Destination.Home -> HomeScreen(platform)
                    Destination.Favorites -> Text(
                        "Inbox"
                    )

                    Destination.VideoResults -> VideoResultsGrid(
                        page.data as? String ?: "",
                        platform
                    )
                }
            }
        }
    }
}