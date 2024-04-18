package presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class Destination(var data: Any? = null) {
    Home, Explore, Favorites, Profile, VideoResults, VideoPlayer, Kyc, DojoLocator
}



@Composable
fun BottomBarItem(destination: Destination, clickAction: (destination: Destination) -> Unit) {
    val icon = when(destination) {
        Destination.Home -> Icons.Default.Home
        Destination.Explore -> Icons.Default.PlayArrow
        Destination.Profile -> Icons.Default.AccountCircle
        Destination.Favorites -> Icons.Default.Favorite
        else -> Icons.Default.Home
    }
    Icon(icon, "", modifier = Modifier.padding(horizontal = 16.dp).clickable { clickAction(destination) })
//    Text(
//        text = destination.name,
//        Modifier.padding(16.dp).clickable { clickAction(destination) })
}