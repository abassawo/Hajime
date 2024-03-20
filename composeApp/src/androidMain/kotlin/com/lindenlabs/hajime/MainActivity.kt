package com.lindenlabs.hajime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import presentation.screens.App
import presentation.screens.onboarding.OnboardingViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val onboardingViewModel = remember { OnboardingViewModel() }
            App(onboardingViewModel)
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App(OnboardingViewModel())
}