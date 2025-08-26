package com.match_intel.android.data.repository

import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.dto.UserDto

interface MatchRepository {
    suspend fun getFollowedMatches(): List<MatchDto>
    suspend fun searchUsers(query: String): List<UserDto>
    suspend fun getMatches(username: String): List<MatchDto>
    suspend fun sendFollowRequest(username: String)
}