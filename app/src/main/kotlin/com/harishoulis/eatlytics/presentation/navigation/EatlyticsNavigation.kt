package com.harishoulis.eatlytics.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.NavStack
import androidx.navigation3.NavStackEntry
import com.harishoulis.eatlytics.presentation.screens.dashboard.DashboardScreen
import com.harishoulis.eatlytics.presentation.screens.food.FoodSearchScreen
import com.harishoulis.eatlytics.presentation.screens.profile.ProfileScreen
import com.harishoulis.eatlytics.presentation.screens.stats.StatsScreen

@Composable
fun EatlyticsNavigation(
    entry: NavStackEntry,
    navStack: NavStack
) {
    when (entry.key) {
        Screen.Dashboard.route -> {
            DashboardScreen(
                onNavigateToFoodSearch = { navStack.push(Screen.FoodSearch.route) },
                onNavigateToStats = { navStack.push(Screen.Stats.route) },
                onNavigateToProfile = { navStack.push(Screen.Profile.route) }
            )
        }
        Screen.FoodSearch.route -> {
            FoodSearchScreen(
                onNavigateBack = { navStack.pop() },
                onFoodSelected = { foodId ->
                    navStack.pop()
                    // Navigate to food details or add to diary
                }
            )
        }
        Screen.Stats.route -> {
            StatsScreen(
                onNavigateBack = { navStack.pop() }
            )
        }
        Screen.Profile.route -> {
            ProfileScreen(
                onNavigateBack = { navStack.pop() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object FoodSearch : Screen("food_search")
    object Stats : Screen("stats")
    object Profile : Screen("profile")
}
