package presentation.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.rememberAsyncImagePainter
import presentation.screens.insights.InsightScreen
import presentation.screens.saved_videos.FavoriteVideoScreen
import presentation.views.Destination
import utils.Platform
import utils.navigation.NavigationStack


@Composable
fun ProfileScreen(
    platform: Platform,
    navigationStack: NavigationStack<Destination>,
    modifier: Modifier = Modifier
) {
    //var userProfile by remember { mutableStateOf(userProfile) }
    val viewModel = platform.profileViewModel
    val state = viewModel.state.collectAsState()

    Column(
        modifier = modifier.background(Color.White).fillMaxSize()
    ) {
        // Profile Image
        Box(
            modifier = Modifier.size(120.dp)
                .padding(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary).clickable {
//                    getContent.launch("image/*") // Calls getContent
                }, contentAlignment = Alignment.Center
        ) {
            if (state.value.image != null) {
                Image(
                    // I replace this line
                    //painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    painter = rememberAsyncImagePainter(
                        model = state.value.image    // or ht
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        platform.onboardingViewModel.kycFormData?.firstName?.let {
            Text(
                text = it,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        platform.onboardingViewModel.beltLevel.let {
            Text(
                text = it.value.name + " belt", modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        val dialogState = remember { mutableStateOf(false) }
        Row(Modifier.fillMaxWidth()) {

            Button(
                onClick = {
                    dialogState.value = true
                }, modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("App Insights", textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    navigationStack.push(Destination.DojoLocator)
                }, modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Find a Gym", textAlign = TextAlign.Center)
            }
        }



        Spacer(Modifier.height(16.dp))
        Divider(Modifier.fillMaxWidth().background(Color.LightGray).height(2.dp))

        Spacer(Modifier.height(16.dp))


        AnimatedVisibility(visible = dialogState.value) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Dialog(onDismissRequest = {

                }) {
                    InsightScreen(platform, onLimitUsageClicked = {
                       // todo  - enable app limit notifications
                    }, onSetReminderClicked = {
                       // todo
                    }){
                        dialogState.value = false
                    }
                }
            }
        }

        Box(Modifier.fillMaxSize().background(Color.Transparent)) {
            TabRow(selectedTabIndex = 0, backgroundColor = Color.Transparent) {
                FavoriteVideoScreen(platform)
            }
        }
    }
}