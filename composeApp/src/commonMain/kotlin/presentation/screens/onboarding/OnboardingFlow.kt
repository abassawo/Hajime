package presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.BeltLevel
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.views.Destination
import utils.navigation.NavigationStack

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun OnboardingFlow(navigationStack: NavigationStack<Destination>) {
    val pageCount = 2
    val pagerState = rememberPagerState(
        initialPage = 0) {
        pageCount
    }
    HorizontalPager(state = pagerState) {
        when(it) {
            0 ->  Box(Modifier.background(Color.Black).fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Welcome to Hajime", color = Color.White)
                    Text(text = "Continue swiping to complete the onboarding", color = Color.White)
                }
            }
            else -> EnterNameAndRankScreen(navigationStack)
            }
        }
    }


@Composable
fun EnterNameAndRankScreen(navigationStack: NavigationStack<Destination>) {
    val viewModel = remember { OnboardingViewModel(navigationStack) }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            TextField(value = viewModel.firstName.value, label = { Text(text = "Enter your first name")  }, onValueChange = {
                viewModel.firstName.value = it
            })

            LazyHorizontalStaggeredGrid(rows = StaggeredGridCells.Adaptive(80.dp), modifier = Modifier.fillMaxWidth()) {
                items(BeltLevel.entries.toTypedArray()) {
                    Text(text = it.name, modifier = Modifier.clickable {
                        viewModel.beltLevel.value = it
                    })
                }
            }

            Button(onClick = { viewModel.submitKyc() }, modifier = Modifier.wrapContentSize()) {
                Text("Submit")
            }
        }
    }
}