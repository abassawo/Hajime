package presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.views.Destination
import utils.navigation.NavigationStack

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun OnboardingFlow(navigationStack: NavigationStack<Destination>) {
    val pageCount = 4
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
            1 -> EnterNameAndRankScreen()
            else -> Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
                        contentAlignment = Alignment.Center
            ) {
                Text(text = "Page Index : $it")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScope.EnterNameAndRankScreen() {
    val viewModel = remember { OnboardingViewModel() }
    var isDropdownExpanded = true
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            TextField(value = viewModel.firstName.value, label = { Text(text = "Enter your first name")  }, onValueChange = {})
            DropdownMenu(expanded = isDropdownExpanded, onDismissRequest = {
                
            }) {
                Text("White Belt", color = Color.Black)
                Text("Blue Belt", color = Color.Black)
                Text("Purple Belt", color = Color.Black)
                Text("Brown Belt", color = Color.Black)
                Text("Black Belt", color = Color.Black)
            }
        }
    }
}