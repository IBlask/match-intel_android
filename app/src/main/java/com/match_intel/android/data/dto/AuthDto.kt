package com.match_intel.android.data.dto

data class RegisterRequest(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val expiresIn: Long
)
