package `in`.androidplay.trackme.ui.compose.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import `in`.androidplay.trackme.ui.compose.home.HomeViewModel

@Composable
internal fun SettingsScreenContent(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    Column(modifier = modifier) {
        Text(text = "Settings")
    }
}