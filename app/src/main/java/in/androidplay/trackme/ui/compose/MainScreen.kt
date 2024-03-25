package `in`.androidplay.trackme.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.services.SortType
import `in`.androidplay.trackme.ui.compose.components.BottomNavigationBar
import `in`.androidplay.trackme.ui.compose.components.HomeAppBar
import `in`.androidplay.trackme.ui.compose.home.HomeViewModel
import `in`.androidplay.trackme.ui.navigation.HomeNavGraph

@Composable
internal fun MainScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
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