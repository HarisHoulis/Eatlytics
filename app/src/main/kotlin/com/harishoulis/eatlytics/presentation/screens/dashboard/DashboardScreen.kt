package com.harishoulis.eatlytics.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.harishoulis.eatlytics.EatlyticsApplication
import com.harishoulis.eatlytics.presentation.mvi.DashboardEffect
import com.harishoulis.eatlytics.presentation.mvi.DashboardIntent
import com.harishoulis.eatlytics.presentation.mvi.DashboardState
import com.harishoulis.eatlytics.presentation.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToFoodSearch: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToProfile: () -> Unit,
) {
    val context = LocalContext.current
    val app = context.applicationContext as EatlyticsApplication
    val factory: ViewModelProvider.Factory = app.appComponent.viewModelFactory()
    val viewModel: DashboardViewModel = ViewModelProvider(
        (context as? androidx.activity.ComponentActivity)
            ?: throw IllegalStateException("Requires ComponentActivity context"),
        factory
    )[DashboardViewModel::class.java]

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is DashboardEffect.NavigateToFoodSearch -> onNavigateToFoodSearch()
                is DashboardEffect.NavigateToStats -> onNavigateToStats()
                is DashboardEffect.NavigateToProfile -> onNavigateToProfile()
                is DashboardEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is DashboardEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.error)
                }

                is DashboardEffect.RefreshCompleted -> {
                    snackbarHostState.showSnackbar("Data refreshed")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eatlytics") },
                actions = {
                    IconButton(onClick = { viewModel.processIntent(DashboardIntent.RefreshData) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    IconButton(onClick = { viewModel.processIntent(DashboardIntent.NavigateToStats) }) {
                        Icon(Icons.Default.ShowChart, contentDescription = "Stats")
                    }
                    IconButton(onClick = { viewModel.processIntent(DashboardIntent.NavigateToProfile) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.processIntent(DashboardIntent.NavigateToFoodSearch) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Food")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                else -> {
                    DashboardContent(
                        state = state,
                        onAddFoodToMeal = { mealType ->
                            viewModel.processIntent(DashboardIntent.AddFoodToMeal(mealType))
                        },
                        onDeleteEntry = { entryId ->
                            viewModel.processIntent(DashboardIntent.DeleteEntry(entryId))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardContent(
    state: DashboardState,
    onAddFoodToMeal: (String) -> Unit,
    onDeleteEntry: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            NutritionSummaryCard(state.dailyNutrition)
        }

        state.entriesByMeal.forEach { (mealType, entries) ->
            item {
                MealSection(
                    mealType = mealType,
                    entries = entries,
                    onAddFood = { onAddFoodToMeal(mealType) },
                    onDeleteEntry = onDeleteEntry
                )
            }
        }
    }
}

@Composable
private fun NutritionSummaryCard(dailyNutrition: com.harishoulis.eatlytics.domain.model.DailyNutritionSummary?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Today's Nutrition",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            dailyNutrition?.let { nutrition ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NutritionItem(
                        label = "Calories",
                        value = "${nutrition.totalCalories.value}",
                        unit = "kcal"
                    )
                    NutritionItem(
                        label = "Protein",
                        value = "${nutrition.totalProtein.value.toInt()}",
                        unit = "g"
                    )
                    NutritionItem(
                        label = "Carbs",
                        value = "${nutrition.totalCarbohydrates.value.toInt()}",
                        unit = "g"
                    )
                    NutritionItem(
                        label = "Fat",
                        value = "${nutrition.totalFat.value.toInt()}",
                        unit = "g"
                    )
                }
            } ?: run {
                Text(
                    text = "No data available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun NutritionItem(
    label: String,
    value: String,
    unit: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = unit,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MealSection(
    mealType: String,
    entries: List<com.harishoulis.eatlytics.domain.model.FoodDiaryEntry>,
    onAddFood: () -> Unit,
    onDeleteEntry: (String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mealType,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onAddFood,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add food to $mealType",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (entries.isEmpty()) {
                Text(
                    text = "No foods added yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                entries.forEach { entry ->
                    FoodEntryItem(
                        entry = entry,
                        onDelete = { onDeleteEntry(entry.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun FoodEntryItem(
    entry: com.harishoulis.eatlytics.domain.model.FoodDiaryEntry,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = entry.food.name.value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${entry.adjustedFood.calories.value} kcal • ${entry.servingSize.value}g",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete entry",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
