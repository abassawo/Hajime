package data

import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import utils.ResourceReader

class LocalDataStore constructor(val resourceReader: ResourceReader) : VimeoService {
    override suspend fun getChannels(): ChannelsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getVideos(channel: String): VideoCollection {
        TODO("Not yet implemented")
    }

    private val json by lazy {
        Json {
            explicitNulls = false
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
            isLenient = true
        }
    }

    override suspend fun searchVideos(query: String): VideoCollection {
        val stringResponse = resourceReader.loadJsonFile().toString()
        println("Json response $stringResponse")
        return json.decodeFromString(stringResponse)
    }
}