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
    val startDate: String,
    val startTime: String,
    val finalScore: String?,
    val visibility: String,
    val finished: Boolean,
    val player1Efficiency: Int,
    val player2Efficiency: Int,
    val numberOfComments: Int,
    val numberOfLikes: Int,
    val likedByUser: Boolean,
    val matchStats: MatchStatsDto?,
    val set1Score: String?,
    val set2Score: String?,
    val set3Score: String?
)

data class MatchStatsDto(
    val point: PointDto,

    val player1Set1Efficiency: Int?,
    val player2Set1Efficiency: Int?,
    val player1Set2Efficiency: Int?,
    val player2Set2Efficiency: Int?,
    val player1Set3Efficiency: Int?,
    val player2Set3Efficiency: Int?,

    val player1Set1ForcedErrors: Int?,
    val player1Set1UnforcedErrors: Int?,
    val player1Set2ForcedErrors: Int?,
    val player1Set2UnforcedErrors: Int?,
    val player1Set3ForcedErrors: Int?,
    val player1Set3UnforcedErrors: Int?,

    val player2Set1ForcedErrors: Int?,
    val player2Set1UnforcedErrors: Int?,
    val player2Set2ForcedErrors: Int?,
    val player2Set2UnforcedErrors: Int?,
    val player2Set3ForcedErrors: Int?,
    val player2Set3UnforcedErrors: Int?,
)
