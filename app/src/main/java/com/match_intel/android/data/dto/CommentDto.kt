package com.match_intel.android.data.dto

data class CommentDto(
    val user: UserDto,
    val comment: String,
    val createdAt: String
)
