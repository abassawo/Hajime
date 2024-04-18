package data

data class ProfileModel(
    val id: Long = 0,
    val name: String,
    val image: String? = null,
    val email: String
)
