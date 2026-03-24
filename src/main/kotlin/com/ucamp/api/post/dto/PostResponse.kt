package com.ucamp.api.post.dto

import java.time.LocalDateTime

data class PostResponse(
    val id: Long?,
    val boardId: Long,
    val boardName: String,
    val userId : Long,
    val nickname: String,
    val title: String,
    val content: String,
    val isAnonymous: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
