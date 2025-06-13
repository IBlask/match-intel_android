package com.match_intel.android.data.repository

import com.match_intel.android.data.dto.LoginResponse

interface AuthRepository {
    suspend fun login(
        username: String,
        password: String
    ) : LoginResponse

    suspend fun register(
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        password: String
    ) : String
}
