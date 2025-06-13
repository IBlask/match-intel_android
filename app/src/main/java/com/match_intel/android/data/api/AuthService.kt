package com.match_intel.android.data.api

import com.match_intel.android.BuildConfig
import com.match_intel.android.data.dto.LoginRequest
import com.match_intel.android.data.dto.LoginResponse
import com.match_intel.android.data.dto.RegisterRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): Map<String, String>

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse


    companion object {
        fun create(): AuthService {
            val logger = HttpLoggingInterceptor().apply {level = Level.BODY}

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService::class.java)
        }
    }
}
