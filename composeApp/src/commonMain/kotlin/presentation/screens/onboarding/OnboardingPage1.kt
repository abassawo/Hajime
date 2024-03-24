package presentation.screens.onboarding


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun OnboardingPage1(nextAction: () -> Unit) {
    Box(Modifier.background(Color.Black).fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to Hajime", color = Color.White)
            Text(text = "Jiujitsu content for all levels", color = Color.White)
            Spacer(Modifier.height(16.dp))
            Button({ nextAction() }) {
                Text("Next")
            }
        }
    }
}