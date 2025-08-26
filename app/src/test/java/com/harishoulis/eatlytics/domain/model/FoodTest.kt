package com.harishoulis.eatlytics.domain.model

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@DisplayName("Food Domain Model Tests")
class FoodTest {

    @Nested
    @DisplayName("Value Classes")
    inner class ValueClasses {

        @Test
        @DisplayName("FoodId should be created from string")
        fun `FoodId should be created from string`() {
            val foodId = FoodId.fromString("test-id")
            expectThat(foodId.value).isEqualTo("test-id")
        }

        @Test
        @DisplayName("Calories should not allow negative values")
        fun `Calories should not allow negative values`() {
            expectThrows<IllegalArgumentException> {
                Calories(-100)
            }
        }

        @Test
        @DisplayName("Calories should support arithmetic operations")
        fun `Calories should support arithmetic operations`() {
            val calories1 = Calories(100)
            val calories2 = Calories(200)

            expectThat(calories1 + calories2).isEqualTo(Calories(300))
            expectThat(calories2 - calories1).isEqualTo(Calories(100))
            expectThat(calories1 - calories2).isEqualTo(Calories(0)) // Should not go negative
        }

        @Test
        @DisplayName("Grams should not allow negative values")
        fun `Grams should not allow negative values`() {
            expectThrows<IllegalArgumentException> {
                Grams(-10.0)
            }
        }

        @Test
        @DisplayName("Grams should support arithmetic operations")
        fun `Grams should support arithmetic operations`() {
            val grams1 = Grams(10.0)
            val grams2 = Grams(5.0)

            expectThat(grams1 + grams2).isEqualTo(Grams(15.0))
            expectThat(grams1 - grams2).isEqualTo(Grams(5.0))
            expectThat(grams1 - grams2).isEqualTo(Grams(5.0))
            expectThat(grams1 * 2.0).isEqualTo(Grams(20.0))
        }

        @Test
        @DisplayName("ServingSize should not allow zero or negative values")
        fun `ServingSize should not allow zero or negative values`() {
            expectThrows<IllegalArgumentException> {
                ServingSize(0.0)
            }
            expectThrows<IllegalArgumentException> {
                ServingSize(-1.0)
            }
        }

        @Test
        @DisplayName("FoodName should not allow blank values")
        fun `FoodName should not allow blank values`() {
            expectThrows<IllegalArgumentException> {
                FoodName("")
            }
            expectThrows<IllegalArgumentException> {
                FoodName("   ")
            }
        }
    }

    @Nested
    @DisplayName("Food Model")
    inner class FoodModel {

        private val sampleFood = Food(
            id = FoodId("test-id"),
            name = FoodName("Chicken Breast"),
            brand = BrandName("Organic Valley"),
            servingSize = ServingSize(100.0),
            calories = Calories(165),
            protein = Grams(31.0),
            carbohydrates = Grams(0.0),
            fat = Grams(3.6)
        )

        @Test
        @DisplayName("Food should calculate calories from macros correctly")
        fun `Food should calculate calories from macros correctly`() {
            val calculatedCalories = sampleFood.calculateCaloriesFromMacros()
            val expectedCalories = (31.0 * 4) + (0.0 * 4) + (3.6 * 9)

            expectThat(calculatedCalories.value).isEqualTo(expectedCalories.toInt())
        }

        @Test
        @DisplayName("Food should validate calorie calculation accuracy")
        fun `Food should validate calorie calculation accuracy`() {
            val accurateFood = sampleFood.copy(
                calories = sampleFood.calculateCaloriesFromMacros()
            )

            expectThat(accurateFood.isCalorieCalculationAccurate()).isTrue()

            val inaccurateFood = sampleFood.copy(
                calories = Calories(200) // Different from calculated
            )

            expectThat(inaccurateFood.isCalorieCalculationAccurate()).isFalse()
        }

        @Test
        @DisplayName("Food should adjust serving size correctly")
        fun `Food should adjust serving size correctly`() {
            val newServingSize = ServingSize(150.0)
            val adjustedFood = sampleFood.adjustServingSize(newServingSize)

            expectThat(adjustedFood.servingSize).isEqualTo(newServingSize)
            expectThat(adjustedFood.calories.value).isEqualTo((165 * 1.5).toInt())
            expectThat(adjustedFood.protein.value).isEqualTo(31.0 * 1.5)
            expectThat(adjustedFood.carbohydrates.value).isEqualTo(0.0)
            expectThat(adjustedFood.fat.value).isEqualTo(3.6 * 1.5)
        }
    }

    @Nested
    @DisplayName("DailyNutritionSummary")
    inner class DailyNutritionSummary {

        private val sampleEntries = listOf(
            FoodDiaryEntry(
                id = "1",
                food = Food(
                    id = FoodId("chicken"),
                    name = FoodName("Chicken Breast"),
                    servingSize = ServingSize(100.0),
                    calories = Calories(165),
                    protein = Grams(31.0),
                    carbohydrates = Grams(0.0),
                    fat = Grams(3.6)
                ),
                servingSize = ServingSize(100.0),
                mealType = MealType.LUNCH,
                timestamp = System.currentTimeMillis()
            ),
            FoodDiaryEntry(
                id = "2",
                food = Food(
                    id = FoodId("rice"),
                    name = FoodName("Brown Rice"),
                    servingSize = ServingSize(100.0),
                    calories = Calories(111),
                    protein = Grams(2.6),
                    carbohydrates = Grams(23.0),
                    fat = Grams(0.9)
                ),
                servingSize = ServingSize(100.0),
                mealType = MealType.LUNCH,
                timestamp = System.currentTimeMillis()
            )
        )

        private val dailySummary = DailyNutritionSummary(
            date = System.currentTimeMillis(),
            totalCalories = Calories(276),
            totalProtein = Grams(33.6),
            totalCarbohydrates = Grams(23.0),
            totalFat = Grams(4.5),
            totalFiber = Grams(0.0),
            totalSugar = Grams(0.0),
            totalSodium = Grams(0.0),
            entries = sampleEntries
        )

        @Test
        @DisplayName("Should calculate remaining calories correctly")
        fun `Should calculate remaining calories correctly`() {
            val dailyGoal = Calories(2000)
            val remaining = dailySummary.remainingCalories(dailyGoal)

            expectThat(remaining.value).isEqualTo(2000 - 276)
        }

        @Test
        @DisplayName("Should calculate macro percentages correctly")
        fun `Should calculate macro percentages correctly`() {
            val percentages = dailySummary.macroPercentages()

            val totalCaloriesFromMacros = (33.6 * 4) + (23.0 * 4) + (4.5 * 9)
            val expectedProtein = (33.6 * 4 / totalCaloriesFromMacros * 100)
            val expectedCarbs = (23.0 * 4 / totalCaloriesFromMacros * 100)
            val expectedFat = (4.5 * 9 / totalCaloriesFromMacros * 100)

            expectThat(percentages.protein).isEqualTo(expectedProtein)
            expectThat(percentages.carbohydrates).isEqualTo(expectedCarbs)
            expectThat(percentages.fat).isEqualTo(expectedFat)
        }
    }

    @Nested
    @DisplayName("MacroPercentages")
    inner class MacroPercentages {

        @Test
        @DisplayName("Should not allow negative percentages")
        fun `Should not allow negative percentages`() {
            expectThrows<IllegalArgumentException> {
                MacroPercentages(protein = -10.0, carbohydrates = 60.0, fat = 50.0)
            }
        }

        @Test
        @DisplayName("Should require percentages to sum to approximately 100%")
        fun `Should require percentages to sum to approximately 100%`() {
            expectThrows<IllegalArgumentException> {
                MacroPercentages(protein = 30.0, carbohydrates = 30.0, fat = 30.0)
            }

            // This should work
            MacroPercentages(protein = 30.0, carbohydrates = 40.0, fat = 30.0)
        }
    }
}
