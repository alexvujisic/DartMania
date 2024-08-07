package com.example.dartmania.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.DartMania.R
import com.example.dartmania.navigation.Screen
import com.example.dartmania.viewmodels.PlayerViewModel

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Darts Mania", false, navController)
        },
        bottomBar = {
            // SimpleBottomAppBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            CardForHomeScreen(
                text = "Play against others?"
            ) {
                navController.navigate(Screen.PlayerGame.route)
            }
            CardForHomeScreen(text = "Play against bot?"){
                navController.navigate(Screen.BotGame.route)
            }
            Image(
                painter = painterResource(id = R.drawable.darts_sketch_vector),
                contentDescription = "Darts Sketch",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )
        }
    }
}