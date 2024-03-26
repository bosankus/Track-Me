package `in`.androidplay.trackme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import `in`.androidplay.trackme.util.Constants.MAIN_GRAPH
import `in`.androidplay.trackme.util.Constants.ONBOARDING_GRAPH

@Composable
fun AppNavigation(isFirstAppOpen: Boolean) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (isFirstAppOpen) ONBOARDING_GRAPH else MAIN_GRAPH
    ) {
        onboardingNavGraph(navController = navController)
        mainNavGraph(navController = navController)
    }
}