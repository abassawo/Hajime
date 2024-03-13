package data

class LocalDataStore : VimeoService {
    override suspend fun getChannels(): ChannelsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getVideos(channel: String): VideoCollection {
        TODO("Not yet implemented")
    }

    override suspend fun searchVideos(query: String): VideoCollection {
        TODO("Not yet implemented")
    }

}