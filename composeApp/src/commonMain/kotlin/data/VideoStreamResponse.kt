package data

import kotlinx.serialization.Serializable

@Serializable
data class VideoStreamResponse(val request: Request?)

@Serializable
data class Request(val files: Files)

@Serializable
data class Files(val progressive: List<Progressive>)

@Serializable
data class Progressive(val id: String, val profile: String, val url: String)

@Serializable
data class VideoStreamModel(val url: String)