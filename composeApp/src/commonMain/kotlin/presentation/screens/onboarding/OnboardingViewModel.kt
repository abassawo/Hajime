package presentation.screens.onboarding

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.BeltLevel

data class KycFormData(val firstName: String, val beltLevel: BeltLevel)

class OnboardingViewModel() {
    var isFirstRun: Boolean = true
    val firstName = mutableStateOf("")
    val beltLevel: MutableState<BeltLevel> = mutableStateOf(BeltLevel.White())
    var kycFormData: KycFormData? = null


    fun isKycComplete(): Boolean = kycFormData != null

    fun submitKyc() {
        kycFormData = KycFormData(firstName.value, beltLevel.value)
        // todo - persist kyc form data
    }
}