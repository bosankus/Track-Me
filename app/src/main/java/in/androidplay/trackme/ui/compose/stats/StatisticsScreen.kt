package `in`.androidplay.trackme.ui.compose.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import `in`.androidplay.trackme.ui.compose.home.HomeViewModel

@Composable
internal fun StatisticScreen(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    Column(modifier = modifier) {
        StatsScreenUI()
    }
}

@Composable
fun StatsScreenUI() {

}