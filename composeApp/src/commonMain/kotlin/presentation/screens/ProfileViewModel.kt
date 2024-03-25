package presentation.screens

import data.ProfileModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.Platform

data class UserProfileState(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val image: String? = null
)


class ProfileViewModel(val platform: Platform) {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state

    init {
        load()
    }

    fun load() {
        platform.coroutineScope.launch {
            val profiles = mutableListOf<UserProfileState>()

            if (profiles.isNotEmpty()) {
                val profile = profiles.first()

                _state.update {
                    it.copy(
                        name = profile.name,
                        image = null,
                        email = profile.email,
                        id = profile.id
                    )
                }

                setProfileImageUri(profile.image)
            }

        }
    }

    fun setName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun setEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun setProfileImageUri(uri: String?) {
        _state.update { it.copy(image = uri) }
    }

    fun saveUserProfile() {
        platform.coroutineScope.launch {
            val state = state.value
            var profile = ProfileModel(
                id = state.id,
                name = state.name,
                email = state.email,
                image = state.image
            )
        }
    }
}