package com.example.dartmania.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.dartmania.viewmodels.PlayerViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel(),
    navController: NavController
) {
    val playerOne by viewModel.playerOne.collectAsStateWithLifecycle()
    val playerTwo by viewModel.playerTwo.collectAsStateWithLifecycle()
    val gameOver by viewModel.gameOver.collectAsStateWithLifecycle()
    val winner by viewModel.winner.collectAsStateWithLifecycle()

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
            PlayerStats(
                pointsRemain = playerOne.pointsRemain,
                average = playerOne.average,
                shots = playerOne.throwsCount,
                name = "Player 1"
            )
            Spacer(modifier = Modifier.height(8.dp))
            PlayerStats(
                pointsRemain = playerTwo.pointsRemain,
                average = playerTwo.average,
                shots = playerTwo.throwsCount,
                name = "Player 2"
            )
            PointButtons { value ->
                when (value) {
                    "DOU" -> viewModel.setMultiplier(2)
                    "TRI" -> viewModel.setMultiplier(3)
                    else -> {
                        val points = value.toIntOrNull() ?: 0
                        viewModel.removePointsFromRemaining(points)
                    }
                }
            }
        }
    }

    if (gameOver) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Game Over") },
            text = { Text(text = "Winner: $winner") },
            confirmButton = {
                Button(onClick = {
                    viewModel.resetGame()
                }) {
                    Text("Play Again")
                }
            }
        )
    }
}
