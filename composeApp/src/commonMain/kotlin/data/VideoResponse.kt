package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoCollection(
    val data: MutableList<Video>
)

@Serializable
data class Video(
    val uri: String?,
    val name: String?,
    val description: String?,
    val type: String?,
    val link: String?,
    val duration: Int,
    val width: Int,
    val language: String?,
    val height: Int,
    val pictures: Pictures,
    val privacy: Privacy?,
    val content_rating: List<String>,
    @SerialName("player_embed_url") val playerEmbedUrl: String
) {
    var streamUrl: String? = null
}

@Serializable
data class Privacy(val view: String, val download: Boolean)
