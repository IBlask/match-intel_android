package com.match_intel.android.data.repository

import com.match_intel.android.data.dto.UserDetailsDto

interface UserRepository {

    suspend fun getUserDetails(username: String): UserDetailsDto
}