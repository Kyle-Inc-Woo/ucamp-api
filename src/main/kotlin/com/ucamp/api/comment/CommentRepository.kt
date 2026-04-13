package com.ucamp.api.comment

import com.ucamp.api.comment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByPostIdOrderByCreatedAtDesc(postId: Long): List<Comment>
}