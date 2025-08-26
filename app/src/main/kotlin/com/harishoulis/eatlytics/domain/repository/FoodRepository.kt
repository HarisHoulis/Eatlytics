package com.harishoulis.eatlytics.domain.repository

import com.harishoulis.eatlytics.domain.model.Food
import com.harishoulis.eatlytics.domain.model.FoodId
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for food-related operations
 */
interface FoodRepository {
    /**
     * Searches for food items by query
     */
    suspend fun searchFood(query: String): List<Food>

    /**
     * Observes food search results as a Flow
     */
    fun observeFoodSearch(query: String): Flow<List<Food>>

    /**
     * Gets a specific food by ID
     */
    suspend fun getFoodById(foodId: String): Food?

    /**
     * Gets a specific food by FoodId
     */
    suspend fun getFoodById(foodId: FoodId): Food? = getFoodById(foodId.value)

    /**
     * Saves a food item
     */
    suspend fun saveFood(food: Food): Result<Food, Throwable>

    /**
     * Gets recently searched foods
     */
    suspend fun getRecentSearches(): List<Food>

    /**
     * Saves a search query for recent searches
     */
    suspend fun saveSearchQuery(query: String)
}
