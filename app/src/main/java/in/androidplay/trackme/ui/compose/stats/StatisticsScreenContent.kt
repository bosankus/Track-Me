package `in`.androidplay.trackme.ui.compose.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import `in`.androidplay.trackme.ui.compose.home.HomeViewModel

@Composable
internal fun StatisticScreenContent(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    Column(modifier = modifier) {
        Text(text = "Statistic")
    }
}