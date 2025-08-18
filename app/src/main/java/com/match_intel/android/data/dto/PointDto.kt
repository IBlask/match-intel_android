package com.match_intel.android.data.dto

data class PointDto(
    val id: String,
    val matchId: String,
    val parentPoint: String,
    val createdAt: String,
    val playerToServe: Int,
    val player1Sets: Int,
    val player2Sets: Int,
    val player1Games: Int,
    val player2Games: Int,
    var player1Points: String,
    var player2Points: String
)
