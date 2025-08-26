package com.harishoulis.eatlytics.domain.repository

import com.harishoulis.eatlytics.domain.model.FoodDiaryEntry
import com.harishoulis.eatlytics.domain.model.MealType
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for food diary operations
 */
interface FoodDiaryRepository {
    /**
     * Adds a food entry to the diary
     */
    suspend fun addEntry(entry: FoodDiaryEntry): Result<FoodDiaryEntry, Throwable>

    /**
     * Updates an existing food diary entry
     */
    suspend fun updateEntry(entry: FoodDiaryEntry): Result<FoodDiaryEntry, Throwable>

    /**
     * Deletes a food diary entry
     */
    suspend fun deleteEntry(entryId: String): Result<Unit, Throwable>

    /**
     * Gets all entries for a specific date range
     */
    suspend fun getEntriesForDateRange(startDate: Long, endDate: Long): List<FoodDiaryEntry>

    /**
     * Observes entries for a specific date range
     */
    fun observeEntriesForDateRange(startDate: Long, endDate: Long): Flow<List<FoodDiaryEntry>>

    /**
     * Gets entries for a specific meal type on a given date
     */
    suspend fun getEntriesForMeal(date: Long, mealType: MealType): List<FoodDiaryEntry>

    /**
     * Observes entries for a specific meal type on a given date
     */
    fun observeEntriesForMeal(date: Long, mealType: MealType): Flow<List<FoodDiaryEntry>>

    /**
     * Gets a specific entry by ID
     */
    suspend fun getEntryById(entryId: String): FoodDiaryEntry?

    /**
     * Clears all entries for a specific date
     */
    suspend fun clearEntriesForDate(date: Long): Result<Unit, Throwable>
}
