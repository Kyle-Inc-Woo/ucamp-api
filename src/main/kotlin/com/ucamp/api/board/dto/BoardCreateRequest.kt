package com.ucamp.api.board.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class BoardCreateRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 50, message = "Name must be 50 more than 50 characters")
    val name: String,

    @field:NotBlank(message = "Description is required")
    @field:Size(max = 255, message = "Description must be at most 255 characters")
    val description: String
)
