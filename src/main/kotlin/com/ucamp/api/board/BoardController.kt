package com.ucamp.api.board

import com.ucamp.api.board.dto.BoardCreateRequest
import com.ucamp.api.board.dto.BoardPatchRequest
import com.ucamp.api.board.dto.BoardResponse
import com.ucamp.api.board.dto.BoardUpdatedRequest
import com.ucamp.api.post.PostService
import com.ucamp.api.post.dto.PostResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService,
    private val postService: PostService
) {
    @GetMapping
    fun getBoards(): ResponseEntity<List<BoardResponse>> {
        return ResponseEntity.ok(boardService.getBoards())
    }

    @GetMapping("/{id}")
    fun getBoard(@PathVariable id: Long): ResponseEntity<BoardResponse> {
        return ResponseEntity.ok(boardService.getBoard(id))
    }

    @GetMapping("/{boardId}/posts")
    fun getPostsByBoard(
        @PathVariable boardId: Long,
        authentication: Authentication?
    ): ResponseEntity<List<PostResponse>> {
        val userId = authentication?.name?.toLongOrNull()
        return ResponseEntity.ok(postService.getPostsByBoard(boardId, userId))
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(@Valid @RequestBody request: BoardCreateRequest): BoardResponse {
        return boardService.createBoard(request)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: BoardUpdatedRequest
    ): BoardResponse {
        return boardService.updateBoard(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteBoard(@PathVariable id: Long): ResponseEntity<Void> {
        boardService.deleteBoard(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}")
    fun patchBoard(
        @PathVariable id: Long,
        @RequestBody request: BoardPatchRequest
    ): ResponseEntity<BoardResponse> {
        return ResponseEntity.ok(boardService.patchBoard(id, request))
    }
}