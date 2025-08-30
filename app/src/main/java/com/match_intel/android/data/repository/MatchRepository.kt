package com.match_intel.android.data.repository

import com.match_intel.android.data.dto.CommentDto
import com.match_intel.android.data.dto.LikeResponse
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.dto.UserDto

interface MatchRepository {
    suspend fun getFollowedMatches(): List<MatchDto>
    suspend fun searchUsers(query: String): List<UserDto>
    suspend fun getMatches(username: String): List<MatchDto>
    suspend fun sendFollowRequest(username: String)
    suspend fun likeMatch(matchId: String): LikeResponse
    suspend fun getLikesList(matchId: String): List<UserDto>
    suspend fun getCommentsList(matchId: String): List<CommentDto>
    suspend fun commentMatch(matchId: String, comment: String): CommentDto
    suspend fun getMatchById(matchId: String): MatchDto
}