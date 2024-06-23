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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.dartmania.viewmodels.PlayerViewModel


// Use the 'viewModel()' function from the lifecycle-viewmodel-compose artifact
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel(),
    navController: NavController
) {
    val playerOne by viewModel.playerOne.collectAsStateWithLifecycle()
    val playerTwo by viewModel.playerTwo.collectAsStateWithLifecycle()
    val currentPlayer by viewModel.currentPlayer.collectAsStateWithLifecycle()
    val rounds by viewModel.rounds.collectAsStateWithLifecycle()

    val gameOver = remember { mutableStateOf(false) }
    val winner = remember { mutableStateOf("") }

    // Update UI elements with uiState
    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Darts Mania", false, navController)
        },
        bottomBar = {
            //SimpleBottomAppBar(navController)
        }
    ){ innerPadding ->
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
                        if (playerOne.pointsRemain == 0) {
                            gameOver.value = true
                            winner.value = playerOne.name
                        } else if (playerTwo.pointsRemain == 0) {
                            gameOver.value = true
                            winner.value = playerTwo.name
                        }
                    }
                }
            }
        }
    }
    if (gameOver.value) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Game Over") },
            text = { Text(text = "Winner: ${winner.value}") },
            confirmButton = {
                Button(onClick = {
                    gameOver.value = false
                    // Reset game logic
                    viewModel.resetGame()
                }) {
                    Text("Play Again")
                }
            }
        )
    }

}

