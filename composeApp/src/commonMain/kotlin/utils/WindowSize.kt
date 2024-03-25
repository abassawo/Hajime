package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

data class WindowSize(
    val width: WindowType,
    val height: WindowType
)

enum class WindowType(val sizeThreshold: Int) {
    Compact(sizeThreshold = 600), Medium(sizeThreshold = 840), Expanded(Int.MAX_VALUE);

    var actualSize: Int = 0
}

@Composable
fun rememberWindowSize(): WindowSize {

    val screenSize = remember { mutableStateOf(Pair(-1, -1)) }
    Layout(
        content = {

        },
        measurePolicy = { measurables, constraints ->
            // Use the max width and height from the constraints
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            screenSize.value = Pair(width, height)
            println("Width: $width, height: $height")

            // Measure and place children composables
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            layout(width, height) {
                var yPosition = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    )

    val screenWidth = screenSize.value.first.dp
    val screenHeight = screenSize.value.second.dp

    return WindowSize(
        width = getScreenWidth(screenWidth.value.toInt()).also { it.actualSize = screenWidth.value.toInt() },
        height = getScreenHeight(screenHeight.value.toInt()).also { it.actualSize = screenHeight.value.toInt() }
    )
}

fun getScreenWidth(width: Int): WindowType = when {
    width < WindowType.Compact.sizeThreshold -> WindowType.Compact
    width < WindowType.Medium.sizeThreshold -> WindowType.Medium
    else -> WindowType.Expanded
}

fun getScreenHeight(height: Int): WindowType = when {
    height < 480 -> WindowType.Compact
    height < 900 -> WindowType.Medium
    else -> WindowType.Expanded
}