package presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.BeltLevel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screens.App
import presentation.views.Destination
import utils.Platform

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun OnboardingFlow(platform: Platform, onFinishKyc: () -> Unit) {
    platform.onboardingViewModel.isFirstRun = false

    val pageCount = 2
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        pageCount
    }
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(state = pagerState) {
        when (it) {
            0 -> OnboardingPage1 {
                coroutineScope.launch {
                    pagerState.scrollToPage(1)
                }
            }
            else -> OnboardingPage2(platform) {
                onFinishKyc()
            }
        }
    }
}

@Composable
fun OnboardingPage2(platform: Platform, nextAction: () -> Unit) {
    EnterNameAndRankScreen(platform) {
        nextAction()
    }
}


@Composable
fun EnterNameAndRankScreen(platform: Platform, action: () -> Unit) {
    val viewModel = remember { OnboardingViewModel() }
    val isKycComplete = mutableStateOf(viewModel.isKycComplete())
    if (isKycComplete.value) {
        App(platform)
    } else {
        Box(Modifier.fillMaxSize().background(Color.LightGray)) {
            Column(Modifier.fillMaxSize()) {
                // Name Field
                OutlinedTextField(
                    value = viewModel.firstName.value,
                    label = { Text(text = "First name") },
                    onValueChange = {
                        viewModel.firstName.value = it
                    },
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )


                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(BeltLevel.entries) {
                        Text(text = it.name, modifier = Modifier.clickable {
                            viewModel.beltLevel.value = it
                        })
                    }
                }

                Button(
                    onClick = {
                        action()
                        viewModel.submitKyc()
                    },
                    modifier = Modifier.wrapContentSize().clickable { action() }
                ) {
                    Text("Submit")
                }
            }
        }
    }
}