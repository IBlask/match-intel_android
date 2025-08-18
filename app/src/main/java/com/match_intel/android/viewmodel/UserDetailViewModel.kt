package com.match_intel.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.repository.MatchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val matchRepository: MatchRepositoryImpl
) : ViewModel() {

    private val _matches = MutableStateFlow<List<MatchDto>>(emptyList())
    val matches: StateFlow<List<MatchDto>> = _matches

    fun fetchMatches(username: String) {
        viewModelScope.launch {
            try {
                _matches.value = matchRepository.getMatches(username)
            } catch (e: Exception) {
                //_matches.value = null
                e.printStackTrace()
            }
        }
    }

    fun sendFollowRequest(username: String) {
        viewModelScope.launch {
            try {
                matchRepository.sendFollowRequest(username)
            } catch (e: Exception) {
                //_matches.value = null
                e.printStackTrace()
            }
        }
    }
}