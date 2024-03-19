package data

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
    val content_rating: List<String>
) {
    var streamUrl: String? = null
}
