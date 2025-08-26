package com.harishoulis.eatlytics.presentation.analytics

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Analytics event interface
 */
interface AnalyticsEvent {
    val eventName: String
    val properties: Map<String, Any>
}

/**
 * Analytics sink for collecting and processing analytics events
 */
@Singleton
class AnalyticsSink @Inject constructor() {

    private val _events = MutableSharedFlow<AnalyticsEvent>()
    val events: SharedFlow<AnalyticsEvent> = _events.asSharedFlow()

    /**
     * Tracks an analytics event
     */
    suspend fun track(event: AnalyticsEvent) {
        _events.emit(event)
    }

    /**
     * Tracks a simple event with name and properties
     */
    suspend fun track(eventName: String, properties: Map<String, Any> = emptyMap()) {
        track(SimpleAnalyticsEvent(eventName, properties))
    }
}

/**
 * Simple analytics event implementation
 */
data class SimpleAnalyticsEvent(
    override val eventName: String,
    override val properties: Map<String, Any>,
) : AnalyticsEvent

/**
 * Predefined analytics events for the app
 */
object AnalyticsEvents {

    // Screen tracking
    object ScreenView {
        fun dashboard() = SimpleAnalyticsEvent("screen_view", mapOf("screen" to "dashboard"))
        fun foodSearch() = SimpleAnalyticsEvent("screen_view", mapOf("screen" to "food_search"))
        fun stats() = SimpleAnalyticsEvent("screen_view", mapOf("screen" to "stats"))
        fun profile() = SimpleAnalyticsEvent("screen_view", mapOf("screen" to "profile"))
    }

    // User actions
    object UserAction {
        fun addFood(foodId: String, mealType: String) = SimpleAnalyticsEvent(
            "add_food",
            mapOf(
                "food_id" to foodId,
                "meal_type" to mealType
            )
        )

        fun deleteEntry(entryId: String) = SimpleAnalyticsEvent(
            "delete_entry",
            mapOf("entry_id" to entryId)
        )

        fun searchFood(query: String) = SimpleAnalyticsEvent(
            "search_food",
            mapOf("query" to query)
        )

        fun updateProfile(properties: Map<String, Any>) = SimpleAnalyticsEvent(
            "update_profile",
            properties
        )

        fun setCalorieGoal(calories: Int) = SimpleAnalyticsEvent(
            "set_calorie_goal",
            mapOf("calories" to calories)
        )

        fun setMacroGoals(protein: Double, carbs: Double, fat: Double) = SimpleAnalyticsEvent(
            "set_macro_goals",
            mapOf(
                "protein" to protein,
                "carbohydrates" to carbs,
                "fat" to fat
            )
        )
    }

    // Navigation
    object Navigation {
        fun navigateToFoodSearch() =
            SimpleAnalyticsEvent("navigate", mapOf("destination" to "food_search"))

        fun navigateToStats() = SimpleAnalyticsEvent("navigate", mapOf("destination" to "stats"))
        fun navigateToProfile() =
            SimpleAnalyticsEvent("navigate", mapOf("destination" to "profile"))
    }

    // Performance
    object Performance {
        fun appStartup(duration: Long) = SimpleAnalyticsEvent(
            "app_startup",
            mapOf("duration_ms" to duration)
        )

        fun screenLoad(screen: String, duration: Long) = SimpleAnalyticsEvent(
            "screen_load",
            mapOf(
                "screen" to screen,
                "duration_ms" to duration
            )
        )
    }

    // Errors
    object Error {
        fun networkError(endpoint: String, errorCode: Int) = SimpleAnalyticsEvent(
            "network_error",
            mapOf(
                "endpoint" to endpoint,
                "error_code" to errorCode
            )
        )

        fun databaseError(operation: String, error: String) = SimpleAnalyticsEvent(
            "database_error",
            mapOf(
                "operation" to operation,
                "error" to error
            )
        )
    }
}

/**
 * Extension function to track analytics events
 */
suspend fun AnalyticsSink.trackScreenView(screen: String) {
    track(
        AnalyticsEvents.ScreenView::class.java.getDeclaredMethod(screen)
            .invoke(null) as AnalyticsEvent
    )
}

/**
 * Extension function to track user actions
 */
suspend fun AnalyticsSink.trackAddFood(foodId: String, mealType: String) {
    track(AnalyticsEvents.UserAction.addFood(foodId, mealType))
}

/**
 * Extension function to track navigation
 */
suspend fun AnalyticsSink.trackNavigation(destination: String) {
    track(
        AnalyticsEvents.Navigation::class.java.getDeclaredMethod("navigateTo${destination.capitalize()}")
            .invoke(null) as AnalyticsEvent
    )
}
