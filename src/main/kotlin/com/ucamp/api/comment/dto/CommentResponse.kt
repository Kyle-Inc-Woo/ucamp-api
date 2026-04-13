package com.ucamp.api.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val nickname: String,
    val content: String,
    val isOwner: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
