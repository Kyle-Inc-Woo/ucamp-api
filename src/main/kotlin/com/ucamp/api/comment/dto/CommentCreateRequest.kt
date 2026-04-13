package com.ucamp.api.comment.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CommentCreateRequest(
    @field:NotNull(message = "postId is required")
    val postId: Long,

    @field:NotBlank(message = "content is required")
    val content: String
)


