import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class Destination {
    Home, Community, Account
}



@Composable
fun BottomBarItem(destination: Destination, clickAction: (destination: Destination) -> Unit) {
    Text(
        text = destination.name,
        Modifier.padding(16.dp).clickable { clickAction(destination) })
}