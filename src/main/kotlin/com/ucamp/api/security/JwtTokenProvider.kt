package com.ucamp.api.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-exp-minutes:60}") private val expMinutes: Long
) {
    /*init {
        println("jwt.secret length = ${secret.length}")
        println("jwt.access-token-exp-minutes = $expMinutes")
        println("jwt.secret preview = ${secret.take(3)}***")
    }  debug code for jwt token issues*/

    private val key by lazy { Keys.hmacShaKeyFor(secret.toByteArray()) }

    fun createAccessToken(userId: Long, email: String): String {
        val now = Date()
        val expiry = Date(now.time + expMinutes * 60 * 1000)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("email", email)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)
            .compact()
    }

    fun validate(token: String): Boolean =
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }

    fun getUserId(token: String): Long {
        val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        return claims.subject.toLong()
    }
}