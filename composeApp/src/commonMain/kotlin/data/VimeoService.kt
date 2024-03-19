package data

interface VimeoService {
    suspend fun getChannels(): ChannelsResponse
    suspend fun getVideos(channel: String): VideoCollection

    suspend fun searchVideos(query: String): VideoCollection

    suspend fun streamVideoFromUrl(restUrl: String): VideoStreamResponse
}