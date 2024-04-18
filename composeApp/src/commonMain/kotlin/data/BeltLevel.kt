package data

import androidx.compose.ui.graphics.Color

sealed class BeltLevel(val name: String, val color: Color, open val stripes: Int) {

    data class White(override val stripes: Int = 0) : BeltLevel("White", Color.White, stripes)
    data class Blue(override val stripes: Int = 0) : BeltLevel("Blue", Color.Blue, stripes)
    data class Purple(override val stripes: Int = 0) : BeltLevel("Purple", Color.Magenta, stripes)
    data class Brown(override val stripes: Int = 0) : BeltLevel("Brown", Color(0xFF6D4C41), stripes)
    data class Black(override val stripes: Int = 0) : BeltLevel("Black", Color.Black, stripes)

    companion object {
        val entries: List<BeltLevel> = listOf(White(), Blue(), Purple(), Brown(), Black())
    }
}