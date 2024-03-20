package presentation.screens.onboarding

import androidx.compose.runtime.mutableStateOf
import data.BeltLevel

class OnboardingViewModel() {
    var isFirstRun: Boolean = true
    val firstName = mutableStateOf("")
    val beltLevel = mutableStateOf(BeltLevel.None)

    fun submitKyc() {
        // save first name
        // save belt level

    }
}