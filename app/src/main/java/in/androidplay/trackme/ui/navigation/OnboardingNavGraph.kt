package `in`.androidplay.trackme.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import `in`.androidplay.trackme.ui.compose.setup.SetupScreen
import `in`.androidplay.trackme.util.Constants.ONBOARDING_GRAPH

fun NavGraphBuilder.onboardingNavGraph(navController: NavController) {
    navigation(
        route = ONBOARDING_GRAPH,
        startDestination = OnboardingScreen.SetupScreen.route
    ) {
        composable(route = OnboardingScreen.SetupScreen.route) {
            SetupScreen(navController = navController)
        }
        // add future onboarding composable here
    }
}