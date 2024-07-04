package com.example.dartmania.viewmodels

import kotlinx.coroutines.*
import kotlin.random.Random

class CPUPlayer(private val callback: (Int, Int) -> Unit) {

    private var job: Job? = null

    fun startGeneratingValues() {
        // Ensure previous job is cancelled if it's still running
        job?.cancel()

        // Start a new coroutine job
        job = CoroutineScope(Dispatchers.Main).launch {
            generateAndPrintValues()
        }
    }

    private suspend fun generateAndPrintValues() {
        repeat(3) {
            delay(2000) // Delay for 2 seconds
            val value = Random.nextInt(1, 21) // Generate a random integer between 1 and 20
            val multiplier = Random.nextInt(1, 4)
            callback(value, multiplier) // Pass the value to the callback function
        }
    }

    fun stopGeneratingValues() {
        // Cancel the coroutine job if needed
        job?.cancel()
    }
}
