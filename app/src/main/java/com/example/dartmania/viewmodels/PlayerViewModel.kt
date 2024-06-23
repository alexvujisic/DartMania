package com.example.dartmania.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dartmania.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel : ViewModel() {
    // Expose screen UI state
    private val _playerOne = MutableStateFlow(Player(name = "Player 1"))
    val playerOne: StateFlow<Player> = _playerOne.asStateFlow()

    private val _playerTwo = MutableStateFlow(Player(name = "Player 2"))
    val playerTwo: StateFlow<Player> = _playerTwo.asStateFlow()

    private val _currentPlayer = MutableStateFlow(1)
    val currentPlayer: StateFlow<Int> = _currentPlayer.asStateFlow()

    private val _rounds = MutableStateFlow(0)
    val rounds: StateFlow<Int> = _rounds.asStateFlow()

    private val _gameOver = MutableStateFlow(false)
    val gameOver: StateFlow<Boolean> = _gameOver.asStateFlow()

    private val _winner = MutableStateFlow("")
    val winner: StateFlow<String> = _winner.asStateFlow()


    private var currentMultiplier = 1

    fun setMultiplier(multiplier: Int) {
        currentMultiplier = multiplier
    }

    fun removePointsFromRemaining(points: Int) {
        val finalPoints = points * currentMultiplier
        currentMultiplier = 1 // Reset multiplier after use

        if (_currentPlayer.value == 1) {
            _playerOne.update { currentState ->
                if (canRemovePoints(currentState.pointsRemain, finalPoints)) {
                    val newPointsRemain = currentState.pointsRemain - finalPoints
                    if (isGameOver(newPointsRemain)) {
                        _gameOver.value = true
                        _winner.value = currentState.name
                        currentState.copy(pointsRemain = 0, throwsCount = currentState.throwsCount + 1)
                    } else {
                        currentState.copy(
                            pointsRemain = newPointsRemain,
                            totalPoints = currentState.totalPoints + finalPoints,
                            throwsCount = currentState.throwsCount + 1
                        )
                    }
                } else {
                    currentState.copy(throwsCount = currentState.throwsCount + 1) // Increment throwsCount even if points exceed remaining points
                }
            }
        } else {
            _playerTwo.update { currentState ->
                if (canRemovePoints(currentState.pointsRemain, finalPoints)) {
                    val newPointsRemain = currentState.pointsRemain - finalPoints
                    if (isGameOver(newPointsRemain)) {
                        _gameOver.value = true
                        _winner.value = currentState.name
                        currentState.copy(pointsRemain = 0, throwsCount = currentState.throwsCount + 1)
                    } else {
                        currentState.copy(
                            pointsRemain = newPointsRemain,
                            totalPoints = currentState.totalPoints + finalPoints,
                            throwsCount = currentState.throwsCount + 1
                        )
                    }
                } else {
                    currentState.copy(throwsCount = currentState.throwsCount + 1) // Increment throwsCount even if points exceed remaining points
                }
            }
        }
        _rounds.value++
        if (_rounds.value % 3 == 0) {
            _currentPlayer.value = if (_currentPlayer.value == 1) 2 else 1
        }
    }


    private fun isGameOver(pointsRemain: Int): Boolean {
        return pointsRemain == 0 && currentMultiplier == 2
    }


    fun resetGame() {
        _playerOne.value = Player(name = "Player 1")
        _playerTwo.value = Player(name = "Player 2")
        _currentPlayer.value = 1
        _rounds.value = 0
    }

    private fun canRemovePoints(pointsRemain: Int, finalPoints: Int): Boolean {
        return finalPoints <= pointsRemain
    }
}
