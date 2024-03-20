package presentation.screens.onboarding

import androidx.compose.runtime.mutableStateOf
import data.BeltLevel
import presentation.views.Destination
import utils.navigation.NavigationStack

class OnboardingViewModel(val navigationStack: NavigationStack<Destination>) {
    val firstName = mutableStateOf("")
    val beltLevel = mutableStateOf(BeltLevel.None)

    fun submitKyc() {
        // save first name
        // save belt level
        navigationStack.push(Destination.Home)
    }
}