package presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import utils.navigation.NavigationStack

@Composable
fun BottomAppBarImpl(navigationStack: NavigationStack<Destination>) {
    val destination = navigationStack.lastWithIndex().value
    if (destination.canShowBottomBar() && navigationStack.lastWithIndex().value != Destination.VideoPlayer) {
        BottomAppBar {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val destinations =
                    listOf(
                        Destination.Home,
                        Destination.Explore,
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
}

private fun Destination.canShowBottomBar() = this != Destination.Kyc
