package com.harishoulis.eatlytics.domain.usecase

import com.harishoulis.eatlytics.domain.model.Calories
import com.harishoulis.eatlytics.domain.model.DailyNutritionSummary
import com.harishoulis.eatlytics.domain.model.FoodDiaryEntry
import com.harishoulis.eatlytics.domain.model.Grams
import com.harishoulis.eatlytics.domain.repository.FoodDiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

/**
 * Context parameter for date operations
 */
context(LocalDate)
fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this / 86400000)

context(LocalDate)
fun LocalDate.toEpochMillis(): Long = this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

/**
 * Use case for getting daily nutrition summary
 */
class GetDailyNutritionUseCase @Inject constructor(
    private val foodDiaryRepository: FoodDiaryRepository,
) {
    /**
     * Gets the daily nutrition summary for a specific date
     */
    suspend operator fun invoke(date: LocalDate): Result<DailyNutritionSummary, Throwable> {
        return try {
            val startOfDay = date.toEpochMillis()
            val endOfDay = startOfDay + 86400000 - 1

            val entries = foodDiaryRepository.getEntriesForDateRange(startOfDay, endOfDay)

            val summary = calculateDailySummary(entries, date)
            Result.success(summary)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Gets daily nutrition summary as a Flow
     */
    fun observeDailyNutrition(date: LocalDate): Flow<Result<DailyNutritionSummary, Throwable>> {
        val startOfDay = date.toEpochMillis()
        val endOfDay = startOfDay + 86400000 - 1

        return foodDiaryRepository.observeEntriesForDateRange(startOfDay, endOfDay)
            .map { entries ->
                try {
                    Result.success(calculateDailySummary(entries, date))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
    }

    private fun calculateDailySummary(
        entries: List<FoodDiaryEntry>,
        date: LocalDate,
    ): DailyNutritionSummary {
        val totalCalories = entries.sumOf { it.adjustedFood.calories.value }.let { Calories(it) }
        val totalProtein = entries.sumOf { it.adjustedFood.protein.value }.let { Grams(it) }
        val totalCarbohydrates =
            entries.sumOf { it.adjustedFood.carbohydrates.value }.let { Grams(it) }
        val totalFat = entries.sumOf { it.adjustedFood.fat.value }.let { Grams(it) }
        val totalFiber = entries.sumOf { it.adjustedFood.fiber.value }.let { Grams(it) }
        val totalSugar = entries.sumOf { it.adjustedFood.sugar.value }.let { Grams(it) }
        val totalSodium = entries.sumOf { it.adjustedFood.sodium.value }.let { Grams(it) }

        return DailyNutritionSummary(
            date = date.toEpochMillis(),
            totalCalories = totalCalories,
            totalProtein = totalProtein,
            totalCarbohydrates = totalCarbohydrates,
            totalFat = totalFat,
            totalFiber = totalFiber,
            totalSugar = totalSugar,
            totalSodium = totalSodium,
            entries = entries
        )
    }
}

/**
 * Extension function for summing Calories
 */
fun Iterable<Calories>.sum(): Calories = Calories(this.sumOf { it.value })

/**
 * Extension function for summing Grams
 */
fun Iterable<Grams>.sum(): Grams = Grams(this.sumOf { it.value })
