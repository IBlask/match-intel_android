package com.match_intel.android.data.dto

data class UserDetailsDto (
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val following: Int,
    val followers: Int,
    val matches: List<MatchDto>
)