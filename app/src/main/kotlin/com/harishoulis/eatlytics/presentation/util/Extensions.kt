package com.harishoulis.eatlytics.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Extension function to collect flows with lifecycle awareness
 */
@Composable
fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): Flow<T> = remember(this, lifecycleOwner) {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
}

/**
 * Extension function to collect StateFlow with lifecycle awareness
 */
@Composable
fun <T> StateFlow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): Flow<T> = remember(this, lifecycleOwner) {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
}

/**
 * Extension function to repeat on lifecycle
 */
suspend fun LifecycleOwner.repeatOnLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend () -> Unit,
) = repeatOnLifecycle(state, block)

/**
 * Extension function to format calories
 */
fun Int.formatCalories(): String = "$this kcal"

/**
 * Extension function to format grams
 */
fun Double.formatGrams(): String = "${this.toInt()}g"

/**
 * Extension function to format percentage
 */
fun Double.formatPercentage(): String = "${this.toInt()}%"

/**
 * Extension function to calculate BMI
 */
fun Pair<Double, Double>.calculateBMI(): Double {
    val (weightKg, heightCm) = this
    val heightM = heightCm / 100
    return weightKg / (heightM * heightM)
}

/**
 * Extension function to get BMI category
 */
fun Double.getBMICategory(): String = when {
    this < 18.5 -> "Underweight"
    this < 25.0 -> "Normal weight"
    this < 30.0 -> "Overweight"
    else -> "Obese"
}

/**
 * Extension function to calculate BMR using Mifflin-St Jeor Equation
 */
fun Triple<Double, Double, Int>.calculateBMR(): Double {
    val (weightKg, heightCm, age) = this
    return 10 * weightKg + 6.25 * heightCm - 5 * age + 5
}

/**
 * Extension function to calculate TDEE
 */
fun Pair<Double, Double>.calculateTDEE(): Double {
    val (bmr, activityMultiplier) = this
    return bmr * activityMultiplier
}
