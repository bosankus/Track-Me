package `in`.androidplay.trackme.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import `in`.androidplay.trackme.ui.compose.MainScreen
import `in`.androidplay.trackme.util.Constants.MAIN_GRAPH

fun NavGraphBuilder.mainNavGraph() {
    navigation(
        route = MAIN_GRAPH,
        startDestination = MainScreens.HomeScreen.route
    ) {
        composable(route = MainScreens.HomeScreen.route) {
            MainScreen()
        }
        // add future onboarding composable here
    }
}