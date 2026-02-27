package com.ucamp.api.auth

import com.ucamp.api.user.UserService
import com.ucamp.api.user.dto.TokenResponse
import com.ucamp.api.user.dto.UserLoginRequest
import com.ucamp.api.user.dto.UserSignupRequest
import com.ucamp.api.user.dto.UserSignupResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(@Valid @RequestBody request: UserSignupRequest): UserSignupResponse {
        return userService.signUp(request)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: UserLoginRequest): TokenResponse {
        return userService.login(request)
    }
}