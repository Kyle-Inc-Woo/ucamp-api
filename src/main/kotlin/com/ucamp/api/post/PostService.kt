package com.ucamp.api.post

import com.ucamp.api.board.BoardRepository
import com.ucamp.api.board.exception.BoardNotFoundException
import com.ucamp.api.common.exception.BadRequestException
import com.ucamp.api.common.exception.UnauthorizedException
import com.ucamp.api.post.domain.Post
import com.ucamp.api.post.dto.PostCreateRequest
import com.ucamp.api.post.dto.PostPatchRequest
import com.ucamp.api.post.dto.PostResponse
import com.ucamp.api.post.exception.PostNotFoundException
import com.ucamp.api.user.UserRepository
import com.ucamp.api.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository
) {

    @Transactional
    fun createPost(request: PostCreateRequest, userId: Long): PostResponse {
        val board = boardRepository.findById(request.boardId)
            .orElseThrow { BoardNotFoundException(request.boardId) }

        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }

        val post = Post(
            board = board,
            user = user,
            title = request.title,
            content = request.content,
            isAnonymous = request.isAnonymous
        )

        val savedPost = postRepository.save(post)
        return savedPost.toResponse()
    }

    fun getPosts(): List<PostResponse> {
        return postRepository.findAllByOrderByCreatedAtDesc()
            .map { it.toResponse() }
    }

    fun getPost(id: Long): PostResponse {
        val post = postRepository.findById(id)
            .orElseThrow { PostNotFoundException(id) }

        return post.toResponse()
    }

    fun getPostsByBoard(boardId: Long): List<PostResponse> {
        boardRepository.findById(boardId)
            .orElseThrow { BoardNotFoundException(boardId) }

        return postRepository.findAllByBoardIdOrderByCreatedAtDesc(boardId)
            .map { it.toResponse() }
    }

    @Transactional
    fun patchPost(id: Long, request: PostPatchRequest, userId: Long): PostResponse {
        val post = postRepository.findById(id)
            .orElseThrow { PostNotFoundException(id) }

        validatePostOwner(post, userId)

        if (request.title == null && request.content == null && request.isAnonymous == null) {
            throw BadRequestException("At least one field must be provided for patch")
        }

        if (request.title != null && request.title.isBlank()) {
            throw BadRequestException("title must not be blank")
        }

        if (request.content != null && request.content.isBlank()) {
            throw BadRequestException("content must not be blank")
        }

        post.patch(
            title = request.title,
            content = request.content,
            isAnonymous = request.isAnonymous
        )

        return post.toResponse()
    }

    @Transactional
    fun deletePost(id: Long, userId: Long) {
        val post = postRepository.findById(id)
            .orElseThrow { PostNotFoundException(id) }

        validatePostOwner(post, userId)

        postRepository.delete(post)
    }

    private fun validatePostOwner(post: Post, userId: Long) {
        if (post.user.id != userId) {
            throw UnauthorizedException(
                errorCode = "POST_FORBIDDEN",
                message = "작성자만 수정 또는 삭제할 수 있습니다."
            )
        }
    }
}