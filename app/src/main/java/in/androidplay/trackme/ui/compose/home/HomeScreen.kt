package `in`.androidplay.trackme.ui.compose.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.room.Run
import `in`.androidplay.trackme.services.SortType
import `in`.androidplay.trackme.ui.compose.components.BottomNavigationBar
import `in`.androidplay.trackme.ui.compose.components.HomeAppBar
import `in`.androidplay.trackme.ui.compose.components.RunViewItem
import `in`.androidplay.trackme.ui.navigation.HomeNavGraph
import `in`.androidplay.trackme.util.ResultData

@Composable
internal fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val navBarItem = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val routeName = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = { HomeAppBar(routeName ?: stringResource(id = R.string.app_name)) },
        content = { innerPadding ->
            HomeNavGraph(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(all = 16.dp),
                viewModel = viewModel,
                navController = navController
            )
        },
        bottomBar = {
            BottomNavigationBar(
                isVisible = navBarItem,
                navController = navController,
            )
        },
        floatingActionButton = {
            FilledTonalButton(onClick = { viewModel.setSortType(SortType.CALORIES) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = stringResource(id = R.string.sort_txt)
                )
            }
        }
    )
}


@Composable
internal fun HomeScreenContent(
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

@Composable
internal fun StatisticScreenContent(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    Column(modifier = modifier) {
        Text(text = "Statistic")
    }
}

@Composable
internal fun SettingsScreenContent(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    Column(modifier = modifier) {
        Text(text = "Settings")
    }
}