package presentation.screens.insights

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import utils.Platform
import utils.SessionData

@Composable
fun InsightScreen(
    platform: Platform,
    onLimitUsageClicked: () -> Unit,
    onSetReminderClicked: () -> Unit,
    dismissAction: () -> Unit,
) {
    val appUsageText = platform.sessionManager.getCurrentSession()?.makeDurationText() ?: "5 minutes"
    Card(modifier = Modifier.clickable { dismissAction() }) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Your current session duration is $appUsageText",
                style = MaterialTheme.typography.body1
            )

            Row {
                Button(
                    modifier = Modifier.alpha(0.8f).weight(1f),
                    onClick = {
                        onLimitUsageClicked()
                        dismissAction()
                    },
                ) {
                    Icon(Icons.Default.Settings, "")
                    Text("Set App limits", fontSize = TextUnit(12f, TextUnitType.Sp), textAlign = TextAlign.Center)
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    modifier = Modifier.alpha(0.8f).weight(1f),
                    onClick = {
                        onSetReminderClicked()
                        dismissAction()

                    },
                ) {
                    Icon(Icons.Default.Notifications, "")
                    Text("Remind me to train!", fontSize = TextUnit(12f, TextUnitType.Sp), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

private fun SessionData.makeDurationText(): String? {
    return "10 hours" // Clock.System.now()- this.sessionStartedAtTime
}
