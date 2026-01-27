package com.ucamp.api.board.dto

import java.time.LocalDateTime

data class BoardResponse(
    val id : Long,
    val name : String,
    val description : String,
    val createdAt : LocalDateTime,
)