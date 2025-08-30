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

sealed class MatchDetailsUiState {
    data object Loading : MatchDetailsUiState()
    data class Success(val match: MatchDto) : MatchDetailsUiState()
    data class Error(val message: String) : MatchDetailsUiState()
}

@HiltViewModel
class MatchStatsViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MatchDetailsUiState>(MatchDetailsUiState.Loading)
    val uiState: StateFlow<MatchDetailsUiState> = _uiState

    fun loadMatch(matchId: String) {
        viewModelScope.launch {
            _uiState.value = MatchDetailsUiState.Loading
            try {
                val match = repository.getMatchById(matchId)
                _uiState.value = MatchDetailsUiState.Success(match)
            } catch (e: Exception) {
                _uiState.value = MatchDetailsUiState.Error(e.message ?: "Greška pri dohvaćanju meča")
            }
        }
    }
}
