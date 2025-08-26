package com.harishoulis.eatlytics.presentation.mvi

import com.harishoulis.eatlytics.domain.model.Food

/**
 * Base interface for all effects in the MVI architecture
 */
interface Effect

/**
 * Effects for the Dashboard screen
 */
sealed class DashboardEffect : Effect {
    object NavigateToFoodSearch : DashboardEffect()
    object NavigateToStats : DashboardEffect()
    object NavigateToProfile : DashboardEffect()
    data class ShowSnackbar(val message: String) : DashboardEffect()
    data class ShowError(val error: String) : DashboardEffect()
    object RefreshCompleted : DashboardEffect()
}

/**
 * Effects for the Food Search screen
 */
sealed class FoodSearchEffect : Effect {
    object NavigateBack : FoodSearchEffect()
    data class NavigateToFoodDetails(val foodId: String) : FoodSearchEffect()
    data class ShowSnackbar(val message: String) : FoodSearchEffect()
    data class ShowError(val error: String) : FoodSearchEffect()
    data class FoodAddedToDiary(val food: Food) : FoodSearchEffect()
}

/**
 * Effects for the Stats screen
 */
sealed class StatsEffect : Effect {
    object NavigateBack : StatsEffect()
    data class ShowSnackbar(val message: String) : StatsEffect()
    data class ShowError(val error: String) : StatsEffect()
    data class ExportStats(val data: String) : StatsEffect()
}

/**
 * Effects for the Profile screen
 */
sealed class ProfileEffect : Effect {
    object NavigateBack : ProfileEffect()
    data class ShowSnackbar(val message: String) : ProfileEffect()
    data class ShowError(val error: String) : ProfileEffect()
    object ProfileSaved : ProfileEffect()
    data class ShowCalorieRecommendation(val calories: Int) : ProfileEffect()
}
