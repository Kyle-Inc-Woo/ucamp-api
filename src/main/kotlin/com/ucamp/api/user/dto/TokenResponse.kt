package com.ucamp.api.user.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val userId: Long,
    val nickname: String
)
