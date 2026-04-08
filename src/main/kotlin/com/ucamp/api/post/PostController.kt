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
    fun createPost(
        @Valid @RequestBody request: PostCreateRequest,
        authentication: Authentication
    ): ResponseEntity<PostResponse> {
        val userId = authentication.name.toLong()
        return ResponseEntity.ok(postService.createPost(request, userId))
    }

    @GetMapping
    fun getPosts(): List<PostResponse> {
        return postService.getPosts()
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): PostResponse {
        return postService.getPost(id)
    }

    @GetMapping("/boards/{boardId}")
    fun getPostsByBoard(@PathVariable boardId: Long): List<PostResponse> {
        return postService.getPostsByBoard(boardId)
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