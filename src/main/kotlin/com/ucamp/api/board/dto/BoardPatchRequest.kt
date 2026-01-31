package com.ucamp.api.board.dto

import jakarta.validation.constraints.Size

data class BoardPatchRequest(
    @field:Size(max = 100)
    val name: String? = null,

    @field:Size(max = 500)
    val description: String? = null
)
