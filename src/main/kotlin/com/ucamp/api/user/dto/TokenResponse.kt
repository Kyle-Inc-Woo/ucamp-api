package com.ucamp.api.user.dto

data class TokenResponse(
    val accessToken: String,
    val tokenType: String = "Bearer"
)
