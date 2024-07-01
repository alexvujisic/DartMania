package com.example.dartmania.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dartmania.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel : ViewModel() {
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

    fun getMultiplier(): Int{
        return currentMultiplier
    }

    fun removePointsFromRemaining(points: Int) {
        val finalPoints = points * currentMultiplier
        val currentState = if (_currentPlayer.value == 1) _playerOne.value else _playerTwo.value

        if (canRemovePoints(currentState.pointsRemain, finalPoints)) {
            val newPointsRemain = currentState.pointsRemain - finalPoints
            if (isGameOver(newPointsRemain)) {
                _gameOver.value = true
                _winner.value = currentState.name
                updatePlayerPoints(0, finalPoints)
            } else {
                updatePlayerPoints(newPointsRemain, finalPoints)
            }
        } else {
            incrementThrowsCount()
        }

        if (_rounds.value % 3 == 0) {
            switchPlayer()
        }
    }

    private fun updatePlayerPoints(newPointsRemain: Int, finalPoints: Int) {
        if (_currentPlayer.value == 1) {
            _playerOne.update {
                it.copy(
                    pointsRemain = newPointsRemain,
                    totalPoints = it.totalPoints + finalPoints,
                    throwsCount = it.throwsCount + 1
                )
            }
        } else {
            _playerTwo.update {
                it.copy(
                    pointsRemain = newPointsRemain,
                    totalPoints = it.totalPoints + finalPoints,
                    throwsCount = it.throwsCount + 1
                )
            }
        }
        _rounds.value++
        currentMultiplier = 1
    }

    private fun incrementThrowsCount() {
        if (_currentPlayer.value == 1) {
            _playerOne.update { it.copy(throwsCount = it.throwsCount + 1) }
        } else {
            _playerTwo.update { it.copy(throwsCount = it.throwsCount + 1) }
        }
        _rounds.value++
        currentMultiplier = 1 // Reset multiplier after overthrow
    }

    private fun switchPlayer() {
        _currentPlayer.value = if (_currentPlayer.value == 1) 2 else 1
    }

    private fun canRemovePoints(pointsRemain: Int, finalPoints: Int): Boolean {
        return if (pointsRemain == finalPoints) {
            currentMultiplier == 2
        } else {
            finalPoints <= pointsRemain
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
        _gameOver.value = false
        _winner.value = ""
    }

}
