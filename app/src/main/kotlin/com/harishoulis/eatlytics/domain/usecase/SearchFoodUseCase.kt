package com.harishoulis.eatlytics.domain.usecase

import com.harishoulis.eatlytics.domain.model.Food
import com.harishoulis.eatlytics.domain.model.FoodName
import com.harishoulis.eatlytics.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for searching food items
 */
class SearchFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
) {
    /**
     * Searches for food items by name
     */
    suspend operator fun invoke(query: String): Result<List<Food>, Throwable> {
        return try {
            require(query.isNotBlank()) { "Search query cannot be blank" }
            require(query.length >= 2) { "Search query must be at least 2 characters" }

            val foods = foodRepository.searchFood(query)
            Result.success(foods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Observes food search results as a Flow
     */
    fun observeSearchResults(query: String): Flow<Result<List<Food>, Throwable>> {
        return try {
            require(query.isNotBlank()) { "Search query cannot be blank" }
            require(query.length >= 2) { "Search query must be at least 2 characters" }

            foodRepository.observeFoodSearch(query)
                .map { foods -> Result.success(foods) }
        } catch (e: Exception) {
            kotlinx.coroutines.flow.flowOf(Result.failure(e))
        }
    }

    /**
     * Gets food by ID
     */
    suspend fun getFoodById(foodId: String): Result<Food, Throwable> {
        return try {
            val food = foodRepository.getFoodById(foodId)
            food?.let { Result.success(it) } ?: Result.failure(FoodNotFoundException(foodId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

/**
 * Custom exception for when food is not found
 */
class FoodNotFoundException(foodId: String) : Exception("Food with ID $foodId not found")

/**
 * Extension function to validate food name
 */
fun FoodName.isValid(): Boolean =
    value.length >= 2 && value.all { it.isLetterOrDigit() || it.isWhitespace() }

/**
 * Extension function to normalize food name for search
 */
fun FoodName.normalizeForSearch(): String = value.trim().lowercase()

/**
 * Extension function to calculate search relevance score
 */
fun Food.calculateSearchRelevance(query: String): Double {
    val normalizedQuery = query.trim().lowercase()
    val normalizedName = name.value.trim().lowercase()

    return when {
        normalizedName.startsWith(normalizedQuery) -> 1.0
        normalizedName.contains(normalizedQuery) -> 0.8
        normalizedName.split(" ").any { it.startsWith(normalizedQuery) } -> 0.6
        else -> 0.0
    }
}
