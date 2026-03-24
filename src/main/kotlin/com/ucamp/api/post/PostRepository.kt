package com.ucamp.api.post

import com.ucamp.api.post.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Post>
    fun findAllByBoardIdOrderByCreatedAtDesc(boardId: Long): List<Post>
}