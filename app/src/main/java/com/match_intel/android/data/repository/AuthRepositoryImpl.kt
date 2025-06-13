package com.match_intel.android.data.repository

import com.match_intel.android.data.api.AuthService
import com.match_intel.android.data.dto.LoginRequest
import com.match_intel.android.data.dto.LoginResponse
import com.match_intel.android.data.dto.RegisterRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun login(username: String, password: String): LoginResponse {
        return authService.login(LoginRequest(username, password))
    }

    override suspend fun register(
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        password: String
    ): String {
        return authService.register(
            RegisterRequest(username, email, firstName, lastName, password)
        )["sessionToken"].toString()
    }
}