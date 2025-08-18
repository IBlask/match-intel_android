package com.match_intel.android.viewmodel

import com.match_intel.android.data.dto.PointDto
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match_intel.android.data.api.MatchService
import com.match_intel.android.data.dto.NewMatchDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val api: MatchService
) : ViewModel() {

    private val _point = MutableStateFlow<PointDto?>(null)
    val point: MutableStateFlow<PointDto?> = _point

    private val _match = MutableStateFlow<NewMatchDto?>(null)
    val match: MutableStateFlow<NewMatchDto?> = _match

    fun createMatch(player1: String, player2: String, server: String, visibility: Int) {
        viewModelScope.launch {
            try {
                _match.value = api.createMatch(player1, player2, server, visibility)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addPoint(matchId: String, scoringPlayerUsername: String) {
        viewModelScope.launch {
            try {
                _point.value = api.addPoint(matchId, scoringPlayerUsername)

                val p1Raw = _point.value!!.player1Points.toInt()
                val p2Raw = _point.value!!.player2Points.toInt()

                val p1Display: String
                val p2Display: String

                if (_point.value!!.player1Games != 6 || _point.value!!.player1Games != 6) {
                    p1Display = when (p1Raw) {
                        0 -> "0"
                        1 -> "15"
                        2 -> "30"
                        3 -> "40"
                        else -> {
                            if (p1Raw == p2Raw) "40"
                            else if (p1Raw > p2Raw) "Ad"
                            else "40"
                        }
                    }

                    p2Display = when (p2Raw) {
                        0 -> "0"
                        1 -> "15"
                        2 -> "30"
                        3 -> "40"
                        else -> {
                            if (p1Raw == p2Raw) "40"
                            else if (p2Raw > p1Raw) "Ad"
                            else "40"
                        }
                    }

                    _point.value = _point.value!!.copy(
                        player1Points = p1Display,
                        player2Points = p2Display
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
