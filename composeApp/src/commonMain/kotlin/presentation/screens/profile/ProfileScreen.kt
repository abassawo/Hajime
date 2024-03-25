package presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.screens.saved_videos.FavoriteVideoScreen
import presentation.views.Destination
import utils.Platform
import utils.navigation.NavigationStack


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(platform: Platform, navigationStack: NavigationStack<Destination>, modifier: Modifier = Modifier) {
    //var userProfile by remember { mutableStateOf(userProfile) }
    val viewModel = platform.profileViewModel
    val state = viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current


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

        // Location Button
        Button(
            onClick = {
                navigationStack.push(Destination.DojoLocator)
            }, modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth().height(50.dp)
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Find a dojo near me")
        }

        Spacer(Modifier.height(16.dp))

        Box(Modifier.fillMaxSize().background(Color.Transparent)) {
            TabRow(selectedTabIndex = 0, backgroundColor = Color.Transparent) {
                FavoriteVideoScreen(platform)
            }
        }
    }
}