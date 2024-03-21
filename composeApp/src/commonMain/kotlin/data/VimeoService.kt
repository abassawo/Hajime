package data

interface VimeoService {
    suspend fun getChannels(): ChannelsResponse
    suspend fun getVideosForChannel(channel: String): VideoCollection

    suspend fun searchVideos(query: String): VideoCollection

    suspend fun streamVideoFromUrl(restUrl: String): VideoStreamResponse
}