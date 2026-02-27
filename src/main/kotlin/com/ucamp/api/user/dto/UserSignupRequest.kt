package com.ucamp.api.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserSignupRequest(
    @field:Email @field:NotBlank
    var email: String,

    @field:NotBlank @field:Size(min = 8, max = 50)
    var password: String,

    @field:NotBlank @field:Size(min = 2, max = 20)
    var nickname: String
)
