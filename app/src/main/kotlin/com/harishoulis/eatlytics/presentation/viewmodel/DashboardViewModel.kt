package com.harishoulis.eatlytics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harishoulis.eatlytics.domain.usecase.GetDailyNutritionUseCase
import com.harishoulis.eatlytics.presentation.mvi.DashboardEffect
import com.harishoulis.eatlytics.presentation.mvi.DashboardIntent
import com.harishoulis.eatlytics.presentation.mvi.DashboardState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getDailyNutritionUseCase: GetDailyNutritionUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<DashboardEffect>()
    val effects: SharedFlow<DashboardEffect> = _effects.asSharedFlow()

    init {
        loadDailyNutrition()
    }

    fun processIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadDailyNutrition -> loadDailyNutrition()
            is DashboardIntent.RefreshData -> refreshData()
            is DashboardIntent.SelectDate -> selectDate(intent.date)
            is DashboardIntent.AddFoodToMeal -> addFoodToMeal(intent.mealType)
            is DashboardIntent.DeleteEntry -> deleteEntry(intent.entryId)
            is DashboardIntent.NavigateToFoodSearch -> navigateToFoodSearch()
            is DashboardIntent.NavigateToStats -> navigateToStats()
            is DashboardIntent.NavigateToProfile -> navigateToProfile()
        }
    }

    private fun loadDailyNutrition() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val currentDate = LocalDate.now()
            getDailyNutritionUseCase(currentDate)
                .onSuccess { dailyNutrition ->
                    val entriesByMeal = dailyNutrition.entries
                        .groupBy { it.mealType.name }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            dailyNutrition = dailyNutrition,
                            entriesByMeal = entriesByMeal
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load daily nutrition"
                        )
                    }
                    _effects.emit(DashboardEffect.ShowError(error.message ?: "Unknown error"))
                }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            loadDailyNutrition()
            _effects.emit(DashboardEffect.RefreshCompleted)
        }
    }

    private fun selectDate(date: Long) {
        viewModelScope.launch {
            _state.update { it.copy(selectedDate = date, isLoading = true) }

            val selectedDate = LocalDate.ofEpochDay(date / 86400000)
            getDailyNutritionUseCase(selectedDate)
                .onSuccess { dailyNutrition ->
                    val entriesByMeal = dailyNutrition.entries
                        .groupBy { it.mealType.name }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            dailyNutrition = dailyNutrition,
                            entriesByMeal = entriesByMeal
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load nutrition for selected date"
                        )
                    }
                    _effects.emit(DashboardEffect.ShowError(error.message ?: "Unknown error"))
                }
        }
    }

    private fun addFoodToMeal(mealType: String) {
        viewModelScope.launch {
            _effects.emit(DashboardEffect.NavigateToFoodSearch)
        }
    }

    private fun deleteEntry(entryId: String) {
        viewModelScope.launch {
            _effects.emit(DashboardEffect.ShowSnackbar("Entry deleted"))
            loadDailyNutrition()
        }
    }

    private fun navigateToFoodSearch() {
        viewModelScope.launch {
            _effects.emit(DashboardEffect.NavigateToFoodSearch)
        }
    }

    private fun navigateToStats() {
        viewModelScope.launch {
            _effects.emit(DashboardEffect.NavigateToStats)
        }
    }

    private fun navigateToProfile() {
        viewModelScope.launch {
            _effects.emit(DashboardEffect.NavigateToProfile)
        }
    }
}
