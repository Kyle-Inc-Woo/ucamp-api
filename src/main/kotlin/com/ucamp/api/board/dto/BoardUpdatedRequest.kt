package com.ucamp.api.board.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class BoardUpdatedRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,

    @field:NotBlank
    @field:Size(max = 5000)
    val description: String

)
