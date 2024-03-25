package presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.detail.VideoPlayerData
import presentation.screens.detail.VideoPlayerScreen
import presentation.screens.explore.ExploreTopicsScreen
import presentation.screens.explore.VideoResultsGrid
import presentation.screens.explore.verticalGradient
import presentation.screens.home.HomeScreen
import presentation.screens.onboarding.OnboardingFlow
import presentation.screens.onboarding.OnboardingViewModel
import presentation.screens.profile.ProfileScreen
import presentation.views.BottomAppBarImpl
import presentation.views.Destination
import presentation.views.MainTopBar
import presentation.views.MapView
import utils.CommonPlatform
import utils.Platform
import utils.navigation.NavigationStack
import utils.rememberWindowSize

@Preview
@Composable
fun App(platform: Platform = CommonPlatform()) {
    val navigationStack = rememberSaveable(
        saver = listSaver(
            restore = { NavigationStack(*it.toTypedArray()) },
            save = { it.stack },
        )
    ) {
        NavigationStack(Destination.Home)
    }
    AppScaffold(platform, navigationStack)
    val onboardingViewModel: OnboardingViewModel = remember { platform.onboardingViewModel }

    LaunchedEffect(Unit) {
        if (onboardingViewModel.isKycComplete().not()) {
            navigationStack.push(Destination.Kyc)
        }
    }
}

@Composable
fun AppScaffold(platform: Platform, navigationStack: NavigationStack<Destination>) {
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
                            navigationStack.push(Destination.Home)
                        }
                    }

                    Destination.Explore -> {
                        ExploreTopicsScreen(platform, navigationStack)
                    }


                    Destination.Profile ->
                        ProfileScreen(
                            platform,
                            navigationStack,
                            Modifier.wrapContentHeight().alpha(0.85f)
                        )


                    Destination.VideoPlayer -> VideoPlayerScreen(
                        platform,
                        navigationStack,
                        page.data as VideoPlayerData
                    )

                    Destination.Home -> HomeScreen(platform, navigationStack)
                    Destination.Favorites -> Text(
                        "Inbox"
                    )

                    Destination.VideoResults -> VideoResultsGrid(
                        page.data as? String ?: "",
                        platform,
                        navigationStack
                    )

                    Destination.DojoLocator -> HajimeBackdropScaffold(frontLayerContent = {
                        MapView(Modifier)
                    }) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(0.85f)
                            ) {
                                Text(
                                    "Find a BJJ Academy near you",
                                    color = Color.White,
                                    style = TextStyle(fontSize = TextUnit(18f, TextUnitType.Sp)),
                                    modifier = Modifier.padding(16.dp)
                                )

                                Spacer(Modifier.height(16.dp))
                            }

                            Text(
                                "As much as we hope you're enjoying learning with the Hajime app, " +
                                        "the best way to master any martial art is with a community and trained instructors ",
                                modifier = Modifier.padding(16.dp)
                            )

                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HajimeBackdropScaffold(
    frontLayerContent: @Composable () -> Unit,
    backLayerContent: @Composable () -> Unit
) {
    val backdropState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )
    LaunchedEffect(backdropState) {
        backdropState.reveal()
    }
    val halfHeightDp = rememberWindowSize().height.actualSize / 2

    BackdropScaffold(
        scaffoldState = backdropState,
        frontLayerScrimColor = Color.Unspecified,
        appBar = {},
        peekHeight = 0.dp,
        headerHeight = halfHeightDp.dp / 2,
        backLayerContent = {
            backLayerContent()
        },
        frontLayerContent = {
            frontLayerContent()
        }
    )
}



