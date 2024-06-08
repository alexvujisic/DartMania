package com.example.dartmania.models

data class Player(
    val name: String,
    var pointsRemain: Int = 501,
    var totalPoints: Int = 0,
    var throwsCount: Int = 0,
    var multiplier: Int = 1,
    var isDouActive: Boolean = false, // Track if DOU button is active
    var isTriActive: Boolean = false // Track if TRI button is active
)