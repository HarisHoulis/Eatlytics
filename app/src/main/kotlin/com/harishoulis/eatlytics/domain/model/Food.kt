package com.harishoulis.eatlytics.domain.model

import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue

/**
 * Value class representing a unique food identifier
 */
@JvmInline
value class FoodId(val value: String) {
    companion object {
        fun fromString(value: String): FoodId = FoodId(value)
    }
}

/**
 * Value class representing calories
 */
@JvmInline
value class Calories(val value: Int) {
    init {
        require(value >= 0) { "Calories cannot be negative" }
    }
    
    operator fun plus(other: Calories): Calories = Calories(value + other.value)
    operator fun minus(other: Calories): Calories = Calories((value - other.value).coerceAtLeast(0))
}

/**
 * Value class representing grams of macronutrients
 */
@JvmInline
value class Grams(val value: Double) {
    init {
        require(value >= 0.0) { "Grams cannot be negative" }
    }
    
    operator fun plus(other: Grams): Grams = Grams(value + other.value)
    operator fun minus(other: Grams): Grams = Grams((value - other.value).coerceAtLeast(0.0))
    operator fun times(multiplier: Double): Grams = Grams(value * multiplier)
}

/**
 * Value class representing serving size
 */
@JvmInline
value class ServingSize(val value: Double) {
    init {
        require(value > 0.0) { "Serving size must be positive" }
    }
}

/**
 * Value class representing food name
 */
@JvmInline
value class FoodName(val value: String) {
    init {
        require(value.isNotBlank()) { "Food name cannot be blank" }
    }
}

/**
 * Value class representing brand name
 */
@JvmInline
value class BrandName(val value: String) {
    init {
        require(value.isNotBlank()) { "Brand name cannot be blank" }
    }
}

/**
 * Domain model representing a food item
 */
@Serializable
data class Food(
    val id: FoodId,
    val name: FoodName,
    val brand: BrandName? = null,
    val servingSize: ServingSize,
    val calories: Calories,
    val protein: Grams,
    val carbohydrates: Grams,
    val fat: Grams,
    val fiber: Grams = Grams(0.0),
    val sugar: Grams = Grams(0.0),
    val sodium: Grams = Grams(0.0)
) {
    /**
     * Calculates total calories from macronutrients
     */
    fun calculateCaloriesFromMacros(): Calories {
        val proteinCalories = protein.value * 4
        val carbCalories = carbohydrates.value * 4
        val fatCalories = fat.value * 9
        return Calories((proteinCalories + carbCalories + fatCalories).toInt())
    }
    
    /**
     * Returns true if the calculated calories match the provided calories
     */
    fun isCalorieCalculationAccurate(): Boolean {
        return calories == calculateCaloriesFromMacros()
    }
    
    /**
     * Creates a new food with adjusted serving size
     */
    fun adjustServingSize(newServingSize: ServingSize): Food {
        val ratio = newServingSize.value / servingSize.value
        return copy(
            servingSize = newServingSize,
            calories = Calories((calories.value * ratio).toInt()),
            protein = protein * ratio,
            carbohydrates = carbohydrates * ratio,
            fat = fat * ratio,
            fiber = fiber * ratio,
            sugar = sugar * ratio,
            sodium = sodium * ratio
        )
    }
}

/**
 * Domain model representing a food diary entry
 */
@Serializable
data class FoodDiaryEntry(
    val id: String,
    val food: Food,
    val servingSize: ServingSize,
    val mealType: MealType,
    val timestamp: Long
) {
    val adjustedFood: Food = food.adjustServingSize(servingSize)
}

/**
 * Enum representing different meal types
 */
enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}

/**
 * Domain model representing daily nutrition summary
 */
@Serializable
data class DailyNutritionSummary(
    val date: Long,
    val totalCalories: Calories,
    val totalProtein: Grams,
    val totalCarbohydrates: Grams,
    val totalFat: Grams,
    val totalFiber: Grams,
    val totalSugar: Grams,
    val totalSodium: Grams,
    val entries: List<FoodDiaryEntry>
) {
    /**
     * Calculates remaining calories based on daily goal
     */
    fun remainingCalories(dailyGoal: Calories): Calories = dailyGoal - totalCalories
    
    /**
     * Calculates macro percentages
     */
    fun macroPercentages(): MacroPercentages {
        val totalCaloriesFromMacros = totalProtein.value * 4 + totalCarbohydrates.value * 4 + totalFat.value * 9
        return MacroPercentages(
            protein = if (totalCaloriesFromMacros > 0) (totalProtein.value * 4 / totalCaloriesFromMacros * 100) else 0.0,
            carbohydrates = if (totalCaloriesFromMacros > 0) (totalCarbohydrates.value * 4 / totalCaloriesFromMacros * 100) else 0.0,
            fat = if (totalCaloriesFromMacros > 0) (totalFat.value * 9 / totalCaloriesFromMacros * 100) else 0.0
        )
    }
}

/**
 * Value class representing macro percentages
 */
@Serializable
data class MacroPercentages(
    val protein: Double,
    val carbohydrates: Double,
    val fat: Double
) {
    init {
        require(protein >= 0.0 && carbohydrates >= 0.0 && fat >= 0.0) {
            "Percentages cannot be negative"
        }
        require((protein + carbohydrates + fat - 100.0).absoluteValue < 1.0) {
            "Percentages should sum to approximately 100%"
        }
    }
}
