package com.match_intel.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match_intel.android.data.dto.UserDetailsDto
import com.match_intel.android.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userDetails = MutableStateFlow<UserDetailsDto?>(null)
    val userDetails: StateFlow<UserDetailsDto?> = _userDetails

    fun loadUserDetails(username: String) {
        viewModelScope.launch {
            val details = userRepository.getUserDetails(username)
            _userDetails.value = details
        }
    }
}