package utils

import hajime.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

actual class ResourceReader {
    @OptIn(ExperimentalResourceApi::class)
    actual suspend fun loadJsonFile(): String {
        return Res.readBytes("files/armbar_200.json").toString()
    }
}
