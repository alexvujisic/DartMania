package com.example.dartmania.models

data class Player(
    val name: String = "default",
    var pointsRemain: Int = 501,
    var totalPoints: Int = 0,
    var throwsCount: Int = 0,
    var multiplier: Int = 1,
    var isDouActive: Boolean = false, // Track if DOU button is active
    var isTriActive: Boolean = false, // Track if TRI button is active
    var checkOutRate: Double = 0.0

    //view model, player 1 player 2 mutable state
){
    val average: Double
        get() = if (throwsCount > 0) totalPoints.toDouble() / throwsCount else 0.0
}