package com.match_intel.android.data.repository

import com.match_intel.android.data.api.UserService
import com.match_intel.android.data.dto.UserDetailsDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun getUserDetails(username: String): UserDetailsDto {
        return userService.getUserDetails(username)
    }
}