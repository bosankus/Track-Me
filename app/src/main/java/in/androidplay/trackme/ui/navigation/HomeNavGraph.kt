package `in`.androidplay.trackme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.androidplay.trackme.ui.compose.home.HomeScreenContent
import `in`.androidplay.trackme.ui.compose.home.HomeViewModel
import `in`.androidplay.trackme.ui.compose.settings.SettingsScreenContent
import `in`.androidplay.trackme.ui.compose.stats.StatisticScreenContent
import `in`.androidplay.trackme.util.Constants.MAIN_GRAPH

@Composable
fun HomeNavGraph(
    modifier: Modifier,
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = MAIN_GRAPH,
        startDestination = BottomNavItem.HomeScreen.route
    ) {
        composable(route = BottomNavItem.HomeScreen.route) {
            HomeScreenContent(modifier = modifier, viewModel = viewModel)
        }
        composable(route = BottomNavItem.StatisticsScreen.route) {
            StatisticScreenContent(modifier = modifier, viewModel = viewModel)
        }
        composable(route = BottomNavItem.SettingsScreen.route) {
            SettingsScreenContent(modifier = modifier, viewModel = viewModel)
        }
    }
}

