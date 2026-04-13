package com.ucamp.api.comment.domain

import com.ucamp.api.comment.dto.CommentResponse
import com.ucamp.api.post.domain.Post
import com.ucamp.api.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun update(content: String) {
        this.content = content
        this.updatedAt = LocalDateTime.now()
    }

    fun toResponse(currentUserId: Long? = null): CommentResponse {
        return CommentResponse(
            id = id,
            postId = post.id,
            userId = user.id,
            nickname = user.nickname,
            content = content,
            isOwner = user.id == currentUserId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}