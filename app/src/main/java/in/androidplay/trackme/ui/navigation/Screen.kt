package `in`.androidplay.trackme.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.ArtTrack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import `in`.androidplay.trackme.R

sealed class OnboardingScreens(val route: String) {
    data object SetupScreen : OnboardingScreens("setup_screen")
}

sealed class MainScreens(val route: String) {
    data object HomeScreen : MainScreens("home_screen")
    data object RunScreen : MainScreens("run_screen")
}

sealed class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val label: Int,
) {
    data object HomeScreen : BottomNavItem(
        route = "Home",
        selectedIcon = Icons.Filled.ArtTrack,
        unselectedIcon = Icons.Outlined.ArtTrack,
        label = R.string.home_nav
    )

    data object StatisticsScreen : BottomNavItem(
        route = "Statistics",
        selectedIcon = Icons.Filled.Analytics,
        unselectedIcon = Icons.Outlined.Analytics,
        label = R.string.statistics_nav
    )

    data object SettingsScreen : BottomNavItem(
        route = "Settings",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        label = R.string.settings_nav
    )
}