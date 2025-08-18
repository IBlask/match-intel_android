package com.match_intel.android.data.dto

data class PlayerDto(
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String
)

data class MatchDto(
    val id: String,
    val player1: PlayerDto,
    val player2: PlayerDto,
    val initialServer: String,
    val startTime: String,
    val finalScore: String?,
    val visibility: String,
    val finished: Boolean
)
