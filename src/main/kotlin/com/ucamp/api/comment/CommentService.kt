package com.ucamp.api.comment

import com.ucamp.api.comment.dto.CommentCreateRequest
import com.ucamp.api.comment.dto.CommentResponse
import com.ucamp.api.comment.domain.Comment
import com.ucamp.api.comment.exception.CommentNotFoundException
import com.ucamp.api.common.exception.UnauthorizedException
import com.ucamp.api.post.PostRepository
import com.ucamp.api.post.exception.PostNotFoundException
import com.ucamp.api.user.UserRepository
import com.ucamp.api.user.exception.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createComment(request: CommentCreateRequest, userId: Long): CommentResponse {
        val post = postRepository.findByIdOrNull(request.postId)
            ?: throw PostNotFoundException(request.postId)

        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException(userId)

        val comment = Comment(
            post = post,
            user = user,
            content = request.content
        )

        return commentRepository.save(comment).toResponse(userId)
    }

    fun getCommentsByPost(postId: Long, userId: Long?): List<CommentResponse> {
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId)
            .map { it.toResponse(userId) }
    }

    @Transactional
    fun deleteComment(id: Long, userId: Long) {
        val comment = commentRepository.findByIdOrNull(id)
            ?: throw CommentNotFoundException(id)

        validateCommentOwner(comment, userId)
        commentRepository.delete(comment)
    }

    private fun validateCommentOwner(comment: Comment, userId: Long) {
        if (comment.user.id != userId) {
            throw UnauthorizedException(
                errorCode = "COMMENT_FORBIDDEN",
                message = "댓글 작성자만 삭제할 수 있습니다."
            )
        }
    }
}