package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelsResponse(
    val total: Int,
    val page: Int,
    @SerialName("per_page") val perPage: Int,
    val paging: Paging,
    val data: List<ChannelRaw>
)

@Serializable
data class Paging(val next: String, val previous: String?, val first: String, val last: String)

interface Channel {
    val uri: String
    val name: String
    val description: String?
}

@Serializable
data class ChannelRaw(
    override val uri: String,
    override val name: String,
    override val description: String?,
    val link: String,
    @SerialName("created_time") val createdTime: String,
    @SerialName("modified_time") val modifiedTime: String,
    val user: User
) : Channel

@Serializable
data class User(
    val uri: String,
    val name: String,
    val link: String,
    val capabilities: Capabilities,
    val location: String,
    val gender: String,
    val bio: String?,
    @SerialName("short_bio") val shortBio: String?,
    @SerialName("created_time") val createdTime: String,
    val pictures: Pictures
)

@Serializable
data class Pictures(
    val uri: String?,
    val active: Boolean,
    val type: String,
    @SerialName("base_link") val baseLink: String,
    val sizes: List<PictureSize>,
    @SerialName("default_picture") val defaultPicture: Boolean
)

@Serializable
data class PictureSize(val width: Int, val height: Int, val link: String, val resourceKey: String?)


@Serializable
data class Capabilities(
    val hasLiveSubscription: Boolean,
    val hasEnterpriseLihp: Boolean,
    val hasSvvTimecodedComments: Boolean = false,
    val hasSimplifiedEnterpriseAccount: Boolean = false
)