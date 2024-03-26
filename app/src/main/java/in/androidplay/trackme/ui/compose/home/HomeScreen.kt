package `in`.androidplay.trackme.ui.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.room.Run
import `in`.androidplay.trackme.ui.compose.components.RunViewItem
import `in`.androidplay.trackme.util.ResultData

@Composable
internal fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    // val sortTypeState = viewModel.sortTypeState.collectAsStateWithLifecycle()
    val runList = viewModel.sortedRunList.collectAsStateWithLifecycle()

    when (runList.value) {
        is ResultData.DoNothing -> {}
        is ResultData.Loading -> {}
        is ResultData.Success -> {
            Column(modifier = modifier) {
                val list = (runList.value as ResultData.Success<List<Run>>).data
                if (list.isNullOrEmpty()) {
                    Text(text = stringResource(id = R.string.empty_list_error_txt))
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(list.size) {
                            RunViewItem(run = list, position = it)
                        }
                    }
                }
            }
        }
        is ResultData.Failed -> {}
    }
}
