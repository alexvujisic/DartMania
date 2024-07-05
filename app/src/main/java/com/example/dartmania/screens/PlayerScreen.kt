package com.example.dartmania.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dartmania.viewmodels.PlayerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel(),
    navController: NavController,
    playingAgainstBot: Boolean = false
) {
    val playerOne by viewModel.playerOne.collectAsStateWithLifecycle()
    val playerTwo by viewModel.playerTwo.collectAsStateWithLifecycle()
    val gameOver by viewModel.gameOver.collectAsStateWithLifecycle()
    val winner by viewModel.winner.collectAsStateWithLifecycle()
    val cpuDarts by viewModel.cpuDarts.collectAsStateWithLifecycle()
    val currentPlayer by viewModel.currentPlayer.collectAsStateWithLifecycle()
    val rounds by viewModel.rounds.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    viewModel.toggleCpuPlayer(playingAgainstBot)



    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Darts Mania", true, navController)
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

            /*Button(onClick = {
                viewModel.toggleCpuPlayer()
            }) {
                Text(text = if(!playerTwo.isCpuPlayer) "Play against CPU" else "Player 2")
            }*/
            DartsPointsRow(
                visible = playerTwo.isCpuPlayer,
                darts = cpuDarts
            )



            ShowCheckout(pointsRemain = playerOne.pointsRemain, player = "Player 1")
            if(!playerTwo.isCpuPlayer){
                ShowCheckout(pointsRemain = playerTwo.pointsRemain, player = "Player 2")
            }


            if(currentPlayer == 1 || (!playerTwo.isCpuPlayer && currentPlayer == 2)){
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
            }else{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            delay(2000) // Simulate delay for CPU throwing
                        }
                    }
                }
            }
        }
    }

    if (gameOver) {
        if((9 == playerOne.throwsCount && playerOne.name == winner) || (9 == playerTwo.throwsCount && playerTwo.name == winner)){
            WinWith9DartsAnimation(
                isVisible = true,
                onAnimationEnd = { viewModel.resetGame() },
                player = winner
            )
        }else{
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
}


//animation während bot spielt