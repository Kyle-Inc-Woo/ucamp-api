package com.ucamp.api.user

import com.ucamp.api.security.JwtTokenProvider
import com.ucamp.api.user.domain.User
import com.ucamp.api.user.dto.TokenResponse
import com.ucamp.api.user.dto.UserLoginRequest
import com.ucamp.api.user.dto.UserSignupRequest
import com.ucamp.api.user.dto.UserSignupResponse
import com.ucamp.api.user.exception.EmailAlreadyExistsException
import com.ucamp.api.user.exception.InvalidCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Transactional
    fun signUp(request: UserSignupRequest): UserSignupResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistsException(request.email)
        }

        val user = User(
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            nickname = request.nickname
        )
        val saved = userRepository.save(user)

        return UserSignupResponse(
            id = saved.id,
            email = saved.email,
            nickname = saved.nickname,
            createdAt = saved.createdAt
        )
    }

    @Transactional(readOnly = true)
    fun login(request: UserLoginRequest): TokenResponse {
        val user = userRepository.findByEmail(request.email) ?: throw InvalidCredentialsException()

        val ok = passwordEncoder.matches(request.password, user.passwordHash)
        if (!ok)
            throw InvalidCredentialsException()

        val token = jwtTokenProvider.createAccessToken(
            userId = user.id,
            email = user.email
        )
        return TokenResponse(accessToken = token)
    }

}