package com.ucamp.api.user.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_users_email", columnNames = ["email"]),
    ]
)

class User(
    @Column(nullable = false, length = 255)
    var email: String,

    @Column(nullable = false, length = 255)
    var passwordHash: String,

    @Column(nullable = false, length = 30)
    var nickname: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}