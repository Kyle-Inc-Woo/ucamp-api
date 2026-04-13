package com.ucamp.api.comment

import com.ucamp.api.comment.dto.CommentCreateRequest
import com.ucamp.api.comment.dto.CommentResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(
        @Valid @RequestBody request: CommentCreateRequest,
        authentication: Authentication
    ): CommentResponse {
        val userId = authentication.name.toLong()
        return commentService.createComment(request, userId)
    }

    @GetMapping("/posts/{postId}")
    fun getComments(
        @PathVariable postId: Long,
        authentication: Authentication?
    ): List<CommentResponse> {
        val userId = authentication?.name?.toLongOrNull()
        return commentService.getCommentsByPost(postId, userId)
    }

    @DeleteMapping("/{id}")
    fun deleteComment(
        @PathVariable id: Long,
        authentication: Authentication
    ): ResponseEntity<Unit> {
        val userId = authentication.name.toLong()
        commentService.deleteComment(id, userId)
        return ResponseEntity.noContent().build()
    }
}