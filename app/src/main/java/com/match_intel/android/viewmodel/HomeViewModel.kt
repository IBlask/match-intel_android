package com.match_intel.android.viewmodel

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.dto.UserDto
import com.match_intel.android.data.repository.MatchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MatchRepositoryImpl,
    private val application: Application
) : ViewModel() {

    private val prefs = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _matches = MutableStateFlow<List<MatchDto>>(emptyList())
    val matches: StateFlow<List<MatchDto>> = _matches

    private val _users = MutableStateFlow<List<UserDto>>(emptyList())
    val users: StateFlow<List<UserDto>> = _users

    init {
        fetchMatches()
    }

    private fun fetchMatches() {
        viewModelScope.launch {
            try {
                _matches.value = repository.getFollowedMatches()
            } catch (e: Exception) {
                //_matches.value = null
                e.printStackTrace()
            }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            try {
                _users.value = repository.searchUsers(query)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        prefs.edit { remove("jwt_token") }
    }
}