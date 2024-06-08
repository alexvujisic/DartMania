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

    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Darts Mania", false, navController)
        },
        bottomBar = {
            //SimpleBottomAppBar(navController)
        }
    ){ innerPadding ->
        Box (
            modifier = Modifier
                .padding(innerPadding),
        ){
            PlayerStats(pointsRemain = pointsRemain)
            Spacer(modifier = Modifier.height(16.dp))
            PointButtons { value ->
                when (value) {
                    "DOU" -> multiplier = 2
                    "TRI" -> multiplier = 3
                    else -> {
                        val intValue = value.toIntOrNull() ?: 0
                        pointsRemain -= intValue * multiplier
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

