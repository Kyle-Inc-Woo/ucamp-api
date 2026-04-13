package com.ucamp.api.post

import com.ucamp.api.post.dto.PostCreateRequest
import com.ucamp.api.post.dto.PostPatchRequest
import com.ucamp.api.post.dto.PostResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(
        @Valid @RequestBody request: PostCreateRequest,
        authentication: Authentication
    ): PostResponse {
        val userId = authentication.name.toLong()
        return postService.createPost(request, userId)
    }

    @GetMapping
    fun getPosts(authentication: Authentication?): List<PostResponse> {
        val userId = authentication?.name?.toLongOrNull()
        return postService.getPosts(userId)
    }

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable id: Long,
        authentication: Authentication?
    ): PostResponse {
        val userId = authentication?.name?.toLongOrNull()
        return postService.getPost(id, userId)
    }

    @GetMapping("/boards/{boardId}")
    fun getPostsByBoard(
        @PathVariable boardId: Long,
        authentication: Authentication?
    ): List<PostResponse> {
        val userId = authentication?.name?.toLongOrNull()
        return postService.getPostsByBoard(boardId, userId)
    }

    @PatchMapping("/{id}")
    fun patchPost(
        @PathVariable id: Long,
        @RequestBody request: PostPatchRequest,
        authentication: Authentication
    ): PostResponse {
        val userId = authentication.name.toLong()
        return postService.patchPost(id, request, userId)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(
        @PathVariable id: Long,
        authentication: Authentication
    ) {
        val userId = authentication.name.toLong()
        postService.deletePost(id, userId)
    }
}