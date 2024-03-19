package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.URLBuilder
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


class VimeoRepository : VimeoService {

    @OptIn(ExperimentalSerializationApi::class)
    private val client: HttpClient =
        HttpClient {
            install(Auth) {
                bearer {
                    sendWithoutRequest {
                        it.headers { append("Authorization", token) }
                        true
                    }
                    BearerTokens("Authorization", token)
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }


    override suspend fun getChannels(): ChannelsResponse {
        val url = "https://api.vimeo.com/channels/"
        val query = URLBuilder(url).buildString()
        return client.get(query).body<ChannelsResponse>().also {
            println("Response $it")
        }
    }

    override suspend fun getVideos(channel: String): VideoCollection {
        val url = "https://api.vimeo.com/channels/$channel/videos"
        val query = URLBuilder(url).buildString()
        return client.get(query).body()
    }

    override suspend fun searchVideos(query: String): VideoCollection {
        val url = "https://api.vimeo.com/videos/"
        val searchQuery = URLBuilder(url).apply {
            parameters.append("query", query)
        }.buildString()
        return client.get(searchQuery).body()

    }

    override suspend fun streamVideoFromUrl(restUrl: String): VideoStreamResponse {
        val urlBuilder = URLBuilder(restUrl).buildString()
        return client.get(urlBuilder).body()
    }


    companion object {
        const val token = "bearer 8de06948a1a1c142577c0df515c14f9f"
    }
}