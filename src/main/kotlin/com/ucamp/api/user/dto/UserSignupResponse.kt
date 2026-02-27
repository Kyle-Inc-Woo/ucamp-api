package com.ucamp.api.user.dto

import java.time.LocalDateTime

data class UserSignupResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val createdAt: LocalDateTime
)
