package com.match_intel.android.viewmodel

import android.app.Application
import android.content.Context
import android.util.Base64
import org.json.JSONObject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match_intel.android.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl,
    application: Application
) : ViewModel() {

    var token by mutableStateOf<String?>(null)
    var error by mutableStateOf<String?>(null)

    private val prefs = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    init {
        token = prefs.getString("jwt_token", null)
        if (token != null && isTokenExpired(token!!)) {
            token = null
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                saveToken(response.token)
                token = response.token
                clearError()
                onSuccess()
            } catch (e: Exception) {
                error = when (e) {
                    is retrofit2.HttpException -> {
                        try {
                            val errorJson = e.response()?.errorBody()?.string()
                            val message =
                                errorJson?.let { JSONObject(it).optString("message", "An error occurred. Please try again.") }
                            message
                        } catch (jsonEx: Exception) {
                            "An unhandled error occurred. Please try again."
                        }
                    }
                    else -> e.localizedMessage ?: "An error occurred. Please try again."
                }
            }
        }
    }

    fun register(
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val sessionToken = repository.register(username, email, firstName, lastName, password)
                saveToken(sessionToken)
                token = sessionToken
                clearError()
                onSuccess()
            } catch (e: Exception) {
                error = when (e) {
                    is retrofit2.HttpException -> {
                        try {
                            val errorJson = e.response()?.errorBody()?.string()
                            val message =
                                errorJson?.let { JSONObject(it).optString("message", "An error occurred. Please try again.") }
                            message
                        } catch (jsonEx: Exception) {
                            "An unhandled error occurred. Please try again."
                        }
                    }
                    else -> e.localizedMessage ?: "An error occurred. Please try again."
                }
            }

        }
    }

    private fun saveToken(token: String) {
        prefs.edit { putString("jwt_token", token) }
    }

    private fun isTokenExpired(token: String): Boolean {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return true

            val payloadJson = String(
                Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_WRAP),
                StandardCharsets.UTF_8
            )
            val payload = JSONObject(payloadJson)

            val exp = payload.optLong("exp", 0L)
            if (exp == 0L) return true

            val nowInSeconds = System.currentTimeMillis() / 1000
            nowInSeconds >= exp
        } catch (e: Exception) {
            true
        }
    }

    fun logout() {
        prefs.edit { remove("jwt_token") }
        token = null
    }

    fun clearError() {
        error = null
    }
}
