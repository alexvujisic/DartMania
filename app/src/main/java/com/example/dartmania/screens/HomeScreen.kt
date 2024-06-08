package com.example.dartmania.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var pointsRemain by remember { mutableStateOf(501) }
    var multiplier by remember { mutableStateOf(1) }
    var totalPoints by remember { mutableStateOf(0) }
    var throwsCount by remember { mutableStateOf(0) }

    val average = if (throwsCount > 0) totalPoints.toDouble() / throwsCount else 0.0


    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Darts Mania", false, navController)
        },
        bottomBar = {
            //SimpleBottomAppBar(navController)
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            PlayerStats(pointsRemain = pointsRemain, average = average)
            Spacer(modifier = Modifier.height(8.dp))
            PlayerStats(pointsRemain = pointsRemain, average = average)
            PointButtons { value ->
                when (value) {
                    "DOU" -> multiplier = 2
                    "TRI" -> multiplier = 3
                    else -> {
                        val intValue = value.toIntOrNull() ?: 0
                        val pointsScored = intValue * multiplier
                        pointsRemain -= pointsScored
                        totalPoints += pointsScored
                        throwsCount++
                        multiplier = 1 // Reset the multiplier after use
                        if (pointsRemain < 0) {
                            pointsRemain = 0 // Ensure points don't go negative
                        }
                    }
                }
            }
        }
    }
}