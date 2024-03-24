package presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationManager
import platform.MapKit.MKCoordinateRegionMake
import platform.MapKit.MKCoordinateSpanMake
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.darwin.NSObject


@Composable
actual fun MapView() {
    GoogleMaps(Modifier)
}

@OptIn(ExperimentalForeignApi::class)
@Composable
fun GoogleMaps(
    modifier: Modifier,
) {
    val locationManager = CLLocationManager()
    val mkMapView = remember {
        MKMapView().apply {
            setUserInteractionEnabled(true)
        }
    }

    // initial height set at 0.dp
    var componentHeight by remember { mutableStateOf(0.dp) }
    // get local density from composable
    val density = LocalDensity.current

    val onValueChange: (text: String) -> Unit = {
        println("value = $it")
    }

    Box(Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            componentHeight = with(density) { // used to put the "my location" to the top of the map
                it.size.height.toDp()
            }
        }
    ) {

        // MapKit
        if (true) {
            UIKitView(
                modifier = modifier.fillMaxSize(),
                interactive = true,
                factory = {
                    mkMapView.delegate = object : NSObject(), MKMapViewDelegateProtocol {
                        // NOTE: Delegates are not currently supported in Kotlin/Native
                        override fun mapViewDidFinishLoadingMap(mapView: MKMapView) {
                            mapView.camera.centerCoordinate =
                                locationManager.location?.coordinate ?: CLLocationCoordinate2DMake(
                                    40.7425954,
                                    -73.933482
                                )
                            mapView.zoomEnabled = true
                            mapView.scrollEnabled = true
                            mapView.showsUserLocation = true
                        }
                    }

                    mkMapView
                },
                update = { view ->
                    val centerCoordinate = CLLocationCoordinate2DMake(40.7425954, -73.933482)
                    view.camera.centerCoordinate =
                        CLLocationCoordinate2DMake(40.7425954, -73.933482)
                    view.zoomEnabled = true
                    view.scrollEnabled = true
                    view.showsUserLocation = true

                    val span = MKCoordinateSpanMake(0.05, 0.05)
                    val region = MKCoordinateRegionMake(centerCoordinate, span = span)
                    view.setRegion(region, animated = true)
                })
        }
    }
}
