package com.harishoulis.eatlytics.presentation.mvi

/**
 * Base interface for all intents in the MVI architecture
 */
interface Intent

/**
 * Intents for the Dashboard screen
 */
sealed class DashboardIntent : Intent {
    object LoadDailyNutrition : DashboardIntent()
    object RefreshData : DashboardIntent()
    data class SelectDate(val date: Long) : DashboardIntent()
    data class AddFoodToMeal(val mealType: String) : DashboardIntent()
    data class DeleteEntry(val entryId: String) : DashboardIntent()
    object NavigateToFoodSearch : DashboardIntent()
    object NavigateToStats : DashboardIntent()
    object NavigateToProfile : DashboardIntent()
}

/**
 * Intents for the Food Search screen
 */
sealed class FoodSearchIntent : Intent {
    data class SearchQuery(val query: String) : FoodSearchIntent()
    data class SelectFood(val foodId: String) : FoodSearchIntent()
    data class AddToDiary(val foodId: String, val servingSize: Double, val mealType: String) :
        FoodSearchIntent()

    object ClearSearch : FoodSearchIntent()
    object NavigateBack : FoodSearchIntent()
}

/**
 * Intents for the Stats screen
 */
sealed class StatsIntent : Intent {
    object LoadWeeklyStats : StatsIntent()
    object LoadMonthlyStats : StatsIntent()
    data class SelectDateRange(val startDate: Long, val endDate: Long) : StatsIntent()
    object NavigateBack : StatsIntent()
}

/**
 * Intents for the Profile screen
 */
sealed class ProfileIntent : Intent {
    data class UpdateDailyCalorieGoal(val calories: Int) : ProfileIntent()
    data class UpdateMacroGoals(val protein: Double, val carbs: Double, val fat: Double) :
        ProfileIntent()

    data class UpdatePersonalInfo(
        val weight: Double,
        val height: Double,
        val age: Int,
        val activityLevel: String,
    ) : ProfileIntent()

    object SaveProfile : ProfileIntent()
    object NavigateBack : ProfileIntent()
}
