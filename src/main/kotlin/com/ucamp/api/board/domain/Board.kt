package com.ucamp.api.board.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "boards")
class Board(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(length = 255)
    var description: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

) {
    fun update(name: String, description: String) {
        this.name = name
        this.description = description
    }

    fun patch(name: String?, description: String?) {
        if (name != null) {
            this.name = name
        }
        if (description != null) {
            this.description = description
        }
    }
}

