package com.match_intel.android.data.api

import com.match_intel.android.data.dto.UserDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("user/{username}")
    suspend fun getUserDetails(@Path("username") username: String): UserDetailsDto
}