package utils

import hajime.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

class ResourceReader {
    @OptIn(ExperimentalResourceApi::class)
    suspend fun loadJsonFile(filePath: String): String {
        return Res.readBytes(filePath).decodeToString()
    }

}