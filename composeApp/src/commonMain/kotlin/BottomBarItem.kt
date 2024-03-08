import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class Destination {
    Home, Community, Account
}

enum class DetailRoute {
    Dictionary
}

sealed class Page {
    data class Main(val destination: Destination = Destination.entries.first()) : Page()

    data class Detail(val detailRoute: DetailRoute) : Page()
}

@Composable
fun BottomBarItem(destination: Destination, clickAction: (destination: Destination) -> Unit) {
    Text(
        text = destination.name,
        Modifier.padding(16.dp).clickable { clickAction(destination) })
}