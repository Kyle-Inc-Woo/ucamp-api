package com.ucamp.api.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserLoginRequest(
    @field:Email @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String
)
