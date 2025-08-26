package com.harishoulis.eatlytics.presentation.mvi

import com.harishoulis.eatlytics.domain.model.DailyNutritionSummary
import com.harishoulis.eatlytics.domain.model.Food
import com.harishoulis.eatlytics.domain.model.FoodDiaryEntry

/**
 * Base interface for all states in the MVI architecture
 */
interface State

/**
 * State for the Dashboard screen
 */
data class DashboardState(
    val isLoading: Boolean = false,
    val dailyNutrition: DailyNutritionSummary? = null,
    val selectedDate: Long = System.currentTimeMillis(),
    val error: String? = null,
    val entriesByMeal: Map<String, List<FoodDiaryEntry>> = emptyMap(),
) : State

/**
 * State for the Food Search screen
 */
data class FoodSearchState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Food> = emptyList(),
    val recentSearches: List<Food> = emptyList(),
    val selectedFood: Food? = null,
    val error: String? = null,
    val isAddingToDiary: Boolean = false,
) : State

/**
 * State for the Stats screen
 */
data class StatsState(
    val isLoading: Boolean = false,
    val weeklyStats: List<DailyNutritionSummary> = emptyList(),
    val monthlyStats: List<DailyNutritionSummary> = emptyList(),
    val selectedDateRange: Pair<Long, Long>? = null,
    val error: String? = null,
) : State

/**
 * State for the Profile screen
 */
data class ProfileState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val dailyCalorieGoal: Int = 2000,
    val macroGoals: MacroGoals = MacroGoals(),
    val personalInfo: PersonalInfo = PersonalInfo(),
    val error: String? = null,
    val isSaved: Boolean = false,
) : State

/**
 * Data class for macro goals
 */
data class MacroGoals(
    val protein: Double = 30.0,
    val carbohydrates: Double = 40.0,
    val fat: Double = 30.0,
) {
    init {
        require(protein + carbohydrates + fat == 100.0) {
            "Macro goals must sum to 100%"
        }
    }
}

/**
 * Data class for personal information
 */
data class PersonalInfo(
    val weight: Double = 70.0,
    val height: Double = 170.0,
    val age: Int = 25,
    val activityLevel: ActivityLevel = ActivityLevel.MODERATE,
)

/**
 * Enum for activity levels
 */
enum class ActivityLevel(val description: String, val multiplier: Double) {
    SEDENTARY("Sedentary", 1.2),
    LIGHT("Lightly Active", 1.375),
    MODERATE("Moderately Active", 1.55),
    ACTIVE("Very Active", 1.725),
    VERY_ACTIVE("Extremely Active", 1.9)
}
