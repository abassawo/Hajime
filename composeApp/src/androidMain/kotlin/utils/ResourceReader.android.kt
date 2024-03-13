package utils

import android.content.Context
import com.lindenlabs.hajime.R


actual class ResourceReader(private val context: Context) {
    actual suspend fun loadJsonFile(): String {
        return context.resources.openRawResource(DATA_FILE_NAME)
            .bufferedReader().use { it.readText() }
    }

    companion object {
        val DATA_FILE_NAME = R.raw.armbar_200
    }
}