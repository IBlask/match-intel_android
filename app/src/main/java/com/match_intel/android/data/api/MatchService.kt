package com.match_intel.android.data.api

import com.match_intel.android.data.dto.CommentDto
import com.match_intel.android.data.dto.LikeResponse
import com.match_intel.android.data.dto.PointDto
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.dto.NewMatchDto
import com.match_intel.android.data.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchService {
    @GET("matches/followed")
    suspend fun getFollowedMatches(): List<MatchDto>

    @GET("user/search")
    suspend fun searchUsers(@Query("query") query: String): List<UserDto>

    @GET("matches/of/{username}")
    suspend fun getMatches(@Path("username") username: String): List<MatchDto>

    @POST("follow/request")
    suspend fun sendFollowRequest(@Query("toUsername") username: String)

    @POST("matches/create")
    suspend fun createMatch(
        @Query("player1") player1: String,
        @Query("player2") player2: String,
        @Query("initialServer") initialServer: String,
        @Query("visibility") visibility: Int
    ): NewMatchDto

    @POST("matches/add_point")
    suspend fun addPoint(
        @Query("matchId") matchId: String,
        @Query("scoringPlayerUsername") scoringPlayerUsername: String,
    ): PointDto

    @POST("matches/like")
    suspend fun likeMatch(@Query("matchId") matchId: String): LikeResponse

    @GET("matches/likes_list")
    suspend fun getLikesList(@Query("matchId") matchId: String): List<UserDto>

    @GET("matches/comments_list/{matchId}")
    suspend fun getCommentsList(@Path("matchId") matchId: String): List<CommentDto>

    @POST("matches/comment/{matchId}")
    suspend fun commentMatch(
        @Path("matchId") matchId: String,
        @Query("comment") comment: String
    ): CommentDto
}
