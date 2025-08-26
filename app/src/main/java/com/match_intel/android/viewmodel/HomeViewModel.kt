package com.match_intel.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {
    private val _matches = MutableStateFlow<List<MatchDto>>(emptyList())
    val matches: StateFlow<List<MatchDto>> = _matches

    fun fetchFollowedMatches() {
        viewModelScope.launch {
            _matches.value = matchRepository.getFollowedMatches()
        }
    }
}