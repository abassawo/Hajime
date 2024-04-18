package presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.lindenlabs.hajime.R

@Composable
actual fun MapView(modifier: Modifier){
    MapInit(Modifier)
}



@Composable
fun MapInit(modifier: Modifier, location: LatLng = LatLng(40.7425954, -73.933482)) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 12f)
    }

    val marker = rememberMarkerState(position = location)
    val builder = LatLngBounds.Builder()
    builder.include(location)
    val mapProperties =
        MapProperties(
            maxZoomPreference = 16f,
            minZoomPreference = 5f,
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(LocalPlatformContext.current, R.raw.google_styles)
        )

    val mapUiSettings = MapUiSettings(mapToolbarEnabled = false)


    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSettings
    ) {
        MarkerInfoWindowContent(
            state = marker,
            title = "My current location",
            onInfoWindowClick = {
                // todo
            }
        ) { marker ->
            MarkerView(title = marker.title ?: "")
        }
    }
}


@Composable
fun MarkerView(title: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }
}