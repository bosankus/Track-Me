package `in`.androidplay.trackme.ui.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import `in`.androidplay.trackme.ui.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(
    isVisible: MutableState<Boolean>,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val screenItems = listOf(
        BottomNavItem.HomeScreen,
        BottomNavItem.StatisticsScreen,
        BottomNavItem.SettingsScreen
    )

    AnimatedVisibility(
        visible = isVisible.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .height(80.dp)
        ) {
            screenItems.forEachIndexed { _, screen ->
                val isSelected = currentRoute == screen.route

                NavigationBarItem(
                    onClick = {
                        navController.navigate(screen.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        if (isSelected)
                            Icon(
                                imageVector = screen.selectedIcon,
                                contentDescription = stringResource(id = screen.label)
                            )
                        else
                            Icon(
                                imageVector = screen.unselectedIcon,
                                contentDescription = stringResource(id = screen.label)
                            )
                    },
                    alwaysShowLabel = true,
                    selected = isSelected,
                    label = { Text(text = screen.route) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BottomNavigationItemDefaults.navigationSelectedItemColor(),
                        unselectedIconColor = BottomNavigationItemDefaults.navigationContentColor(),
                        selectedTextColor = BottomNavigationItemDefaults.navigationSelectedItemColor(),
                        unselectedTextColor = BottomNavigationItemDefaults.navigationContentColor(),
                        indicatorColor = BottomNavigationItemDefaults.navigationIndicatorColor(),
                    )
                )
            }
        }
    }
}

object BottomNavigationItemDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}