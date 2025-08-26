package com.match_intel.android.data.repository

import com.match_intel.android.data.api.MatchService
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.dto.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchRepositoryImpl @Inject constructor (
    private val matchService: MatchService
) : MatchRepository {

    override suspend fun getFollowedMatches(): List<MatchDto> {
        return matchService.getFollowedMatches()
    }

    override suspend fun searchUsers(query: String): List<UserDto> {
        return matchService.searchUsers(query)
    }

    override suspend fun getMatches(username: String): List<MatchDto> {
        return matchService.getMatches(username)
    }

    override suspend fun sendFollowRequest(username: String) {
        return matchService.sendFollowRequest(username)
    }
}