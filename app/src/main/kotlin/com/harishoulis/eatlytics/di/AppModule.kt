package com.harishoulis.eatlytics.di

import com.harishoulis.eatlytics.domain.repository.FoodDiaryRepository
import com.harishoulis.eatlytics.domain.repository.FoodRepository
import com.harishoulis.eatlytics.domain.usecase.GetDailyNutritionUseCase
import com.harishoulis.eatlytics.domain.usecase.SearchFoodUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {


    @Provides
    @Singleton
    fun provideFoodRepository(): FoodRepository {
        return object : FoodRepository {
            override suspend fun searchFood(query: String): List<com.harishoulis.eatlytics.domain.model.Food> =
                emptyList()

            override fun observeFoodSearch(query: String): kotlinx.coroutines.flow.Flow<List<com.harishoulis.eatlytics.domain.model.Food>> =
                kotlinx.coroutines.flow.flowOf(emptyList())

            override suspend fun getFoodById(foodId: String): com.harishoulis.eatlytics.domain.model.Food? =
                null

            override suspend fun saveFood(food: com.harishoulis.eatlytics.domain.model.Food): com.michaelbull.kotlin.result.Result<com.harishoulis.eatlytics.domain.model.Food, Throwable> =
                com.michaelbull.kotlin.result.Result.failure(Exception("Not implemented"))

            override suspend fun getRecentSearches(): List<com.harishoulis.eatlytics.domain.model.Food> =
                emptyList()

            override suspend fun saveSearchQuery(query: String) {}
        }
    }

    @Provides
    @Singleton
    fun provideFoodDiaryRepository(): FoodDiaryRepository {
        return object : FoodDiaryRepository {
            override suspend fun addEntry(entry: com.harishoulis.eatlytics.domain.model.FoodDiaryEntry): com.michaelbull.kotlin.result.Result<com.harishoulis.eatlytics.domain.model.FoodDiaryEntry, Throwable> =
                com.michaelbull.kotlin.result.Result.failure(Exception("Not implemented"))

            override suspend fun updateEntry(entry: com.harishoulis.eatlytics.domain.model.FoodDiaryEntry): com.michaelbull.kotlin.result.Result<com.harishoulis.eatlytics.domain.model.FoodDiaryEntry, Throwable> =
                com.michaelbull.kotlin.result.Result.failure(Exception("Not implemented"))

            override suspend fun deleteEntry(entryId: String): com.michaelbull.kotlin.result.Result<Unit, Throwable> =
                com.michaelbull.kotlin.result.Result.failure(Exception("Not implemented"))

            override suspend fun getEntriesForDateRange(
                startDate: Long,
                endDate: Long,
            ): List<com.harishoulis.eatlytics.domain.model.FoodDiaryEntry> = emptyList()

            override fun observeEntriesForDateRange(
                startDate: Long,
                endDate: Long,
            ): kotlinx.coroutines.flow.Flow<List<com.harishoulis.eatlytics.domain.model.FoodDiaryEntry>> =
                kotlinx.coroutines.flow.flowOf(emptyList())

            override suspend fun getEntriesForMeal(
                date: Long,
                mealType: com.harishoulis.eatlytics.domain.model.MealType,
            ): List<com.harishoulis.eatlytics.domain.model.FoodDiaryEntry> = emptyList()

            override fun observeEntriesForMeal(
                date: Long,
                mealType: com.harishoulis.eatlytics.domain.model.MealType,
            ): kotlinx.coroutines.flow.Flow<List<com.harishoulis.eatlytics.domain.model.FoodDiaryEntry>> =
                kotlinx.coroutines.flow.flowOf(emptyList())

            override suspend fun getEntryById(entryId: String): com.harishoulis.eatlytics.domain.model.FoodDiaryEntry? =
                null

            override suspend fun clearEntriesForDate(date: Long): com.michaelbull.kotlin.result.Result<Unit, Throwable> =
                com.michaelbull.kotlin.result.Result.failure(Exception("Not implemented"))
        }
    }

    @Provides
    @Singleton
    fun provideGetDailyNutritionUseCase(
        foodDiaryRepository: FoodDiaryRepository,
    ): GetDailyNutritionUseCase = GetDailyNutritionUseCase(foodDiaryRepository)

    @Provides
    @Singleton
    fun provideSearchFoodUseCase(
        foodRepository: FoodRepository,
    ): SearchFoodUseCase = SearchFoodUseCase(foodRepository)
}
