package com.example.dartmania.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dartmania.viewmodels.PlayerViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel(),
    navController: NavController
) {
    val playerOne by viewModel.playerOne.collectAsStateWithLifecycle()
    val playerTwo by viewModel.playerTwo.collectAsStateWithLifecycle()
    val gameOver by viewModel.gameOver.collectAsStateWithLifecycle()
    val winner by viewModel.winner.collectAsStateWithLifecycle()
    val cpuDarts by viewModel.cpuDarts.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Darts Mania", false, navController)
        },
        bottomBar = {
            // SimpleBottomAppBar(navController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                name = if (playerTwo.isCpuPlayer) "CPU" else "Player 2"
            )
            Button(onClick = {
                viewModel.resetGame()
                viewModel.toggleCpuPlayer()
            }) {
                Text(text = "Play against CPU")
            }
            if (playerTwo.isCpuPlayer){
                Row {
                    Text (text = "" + cpuDarts)
                }
            }
            PointButtons { value ->
                when (value) {
                    "DOU" -> viewModel.setMultiplier(2)
                    "TRI" -> viewModel.setMultiplier(3)
                    else -> {
                        val points = value.toIntOrNull() ?: 0
                        //TRI 25 does not exist, check if player input is TRI 25
                        if (points == 25 && viewModel.getMultiplier() == 3) {
                            // Invalid combination, reset the multiplier and show a message
                            viewModel.setMultiplier(1)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("TRI 25 is not allowed")
                            }
                        } else {
                            viewModel.removePointsFromRemaining(points)
                        }
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


//animation während bot spielt