package com.example.dartmania.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Darts Mania", false, navController)
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ){ innerPadding ->

        Box (
            modifier = Modifier
                .padding(innerPadding),
        ){
            PlayerStats()
            PointButtons()
        }
    }
}

