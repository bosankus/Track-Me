package `in`.androidplay.trackme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import `in`.androidplay.trackme.ui.compose.MainScreen
import `in`.androidplay.trackme.ui.compose.home.HomeViewModel
import `in`.androidplay.trackme.ui.compose.run.RunScreen
import `in`.androidplay.trackme.util.Constants.MAIN_GRAPH

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        route = MAIN_GRAPH,
        startDestination = MainScreens.HomeScreen.route
    ) {
        composable(route = MainScreens.HomeScreen.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            MainScreen(viewModel = viewModel)
        }
        composable(route = MainScreens.RunScreen.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            RunScreen(viewModel = viewModel)
        }
        // add future onboarding composable here
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}