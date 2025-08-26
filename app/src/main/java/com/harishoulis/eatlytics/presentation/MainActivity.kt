package com.harishoulis.eatlytics.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.NavDisplay
import androidx.navigation3.rememberNavStack
import com.harishoulis.eatlytics.presentation.navigation.EatlyticsNavigation
import com.harishoulis.eatlytics.presentation.ui.theme.EatlyticsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatlyticsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EatlyticsApp()
                }
            }
        }
    }
}

@Composable
fun EatlyticsApp() {
    val navStack = rememberNavStack()
    
    NavDisplay(
        navStack = navStack,
        modifier = Modifier.fillMaxSize()
    ) { entry ->
        EatlyticsNavigation(entry = entry, navStack = navStack)
    }
}
