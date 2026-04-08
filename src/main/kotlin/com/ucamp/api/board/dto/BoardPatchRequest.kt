package com.ucamp.api.board.dto

import jakarta.validation.constraints.Size

data class BoardPatchRequest(
    @field:Size(max = 50, message = "Name must be at most 50 characters")
    val name: String? = null,

    @field:Size(max = 255, message = "Name must be at most 255 characters")
    val description: String? = null
)
