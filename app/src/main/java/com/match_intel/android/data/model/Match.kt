package com.match_intel.android.data.model

import java.time.LocalDateTime
import java.util.UUID

data class Match(
    val id: UUID,
    val player1: String,
    val player2: String,
    val initialServer: String,
    val startTime: LocalDateTime,
    val isFinished: Boolean,
    val finalScore: String,
    val visibility: String,
    val player1Efficiency: Int?,
    val player2Efficiency: Int?
)