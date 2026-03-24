package com.ucamp.api.post.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PostCreateRequest(
    @field:NotNull(message = "boardId is required")
    val boardId: Long,

    @field:NotBlank(message = "title is required")
    @field:Size(max =120, message = "title must be at most 120 characters")
    val title: String,

    @field:NotBlank(message = "content is required")
    val content: String,

    val isAnonymous: Boolean = false
)
