package com.ucamp.api.post.domain

import com.ucamp.api.board.domain.Board
import com.ucamp.api.post.dto.PostResponse
import com.ucamp.api.user.domain.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    val board: Board,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false, length = 120)
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "is_anonymous", nullable = false)
    var isAnonymous: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun update(title: String, content: String, isAnonymous: Boolean) {
        this.title = title
        this.content = content
        this.isAnonymous = isAnonymous
        this.updatedAt = LocalDateTime.now()
    }

    fun patch(title: String?, content: String?, isAnonymous: Boolean?) {
        if (title != null) {
            this.title = title
        }
        if (content != null) {
            this.content = content
        }
        if (isAnonymous != null) {
            this.isAnonymous = isAnonymous
        }
        this.updatedAt = LocalDateTime.now()
    }

    fun toResponse(currentUserId: Long? = null): PostResponse {
        val owner = user.id == currentUserId
        val masked = isAnonymous && !owner

        return PostResponse(
            id = id,
            boardId = board.id!!,
            boardName = board.name,
            userId = if (masked) null else user.id,
            nickname = if (masked) "익명" else user.nickname,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isOwner = owner,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}