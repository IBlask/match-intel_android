package com.match_intel.android.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match_intel.android.data.dto.CommentDto
import com.match_intel.android.data.dto.MatchDto
import com.match_intel.android.data.dto.UserDto
import com.match_intel.android.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {
    private val _matches = MutableStateFlow<List<MatchDto>>(emptyList())
    val matches: StateFlow<List<MatchDto>> = _matches

    private val _comments = MutableStateFlow<List<CommentDto>>(emptyList())
    val comments: StateFlow<List<CommentDto>> = _comments

    fun fetchFollowedMatches() {
        viewModelScope.launch {
            _matches.value = matchRepository.getFollowedMatches()
        }
    }

    fun onLikeClick(matchId: String) {
        viewModelScope.launch {
            val current = _matches.value.toMutableList()
            val index = current.indexOfFirst { it.id == matchId }
            if (index == -1) return@launch

            val match = current[index]

            val newLiked = !match.likedByUser
            val newLikes = if (newLiked) match.numberOfLikes + 1 else maxOf(0, match.numberOfLikes - 1)
            current[index] = match.copy(likedByUser = newLiked, numberOfLikes = newLikes)
            _matches.value = current

            try {
                val response = matchRepository.likeMatch(matchId)
                val optimisticMatch = current[index]

                val finalLikes = if (response.liked) {
                    optimisticMatch.numberOfLikes
                } else {
                    maxOf(0, optimisticMatch.numberOfLikes - 1)
                }

                current[index] = optimisticMatch.copy(
                    likedByUser = response.liked,
                    numberOfLikes = finalLikes
                )
                _matches.value = current
            } catch (e: Exception) {
                current[index] = match
                _matches.value = current
            }
        }
    }

    fun getLikesList(matchId: String): List<UserDto> {
        var result: List<UserDto> = emptyList()
        runBlocking {
            try {
                result = matchRepository.getLikesList(matchId)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching likes list", e)
            }
        }
        return result
    }

    fun loadComments(matchId: String) {
        viewModelScope.launch {
            try {
                _comments.value = matchRepository.getCommentsList(matchId)
            } catch (e: Exception) {
                _comments.value = emptyList()
            }
        }
    }

    fun commentMatch(matchId: String, comment: String) {
        viewModelScope.launch {
            try {
                val addedComment = matchRepository.commentMatch(matchId, comment)
                _comments.value += addedComment
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error posting comment", e)
            }
        }
    }


}