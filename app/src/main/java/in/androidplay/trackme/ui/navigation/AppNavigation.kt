package `in`.androidplay.trackme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.androidplay.trackme.ui.compose.components.sharedViewModel
import `in`.androidplay.trackme.ui.compose.home.HomeScreen
import `in`.androidplay.trackme.ui.compose.home.HomeViewModel
import `in`.androidplay.trackme.util.Constants.HOME_GRAPH
import `in`.androidplay.trackme.util.Constants.ONBOARDING_GRAPH

@Composable
fun AppNavigation(isFirstAppOpen: Boolean) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (isFirstAppOpen) ONBOARDING_GRAPH else HOME_GRAPH
    ) {
        onboardingNavGraph(navController = navController)
        composable(route = HOME_GRAPH) {
            HomeScreen()
        }
    }
}