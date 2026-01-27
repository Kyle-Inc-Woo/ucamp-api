package com.ucamp.api.board

import com.ucamp.api.board.dto.BoardResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/boards")

class BoardController (
    private val boardService : BoardService
) {
  @GetMapping
  fun getBoards() : ResponseEntity<List<BoardResponse>> {
      return ResponseEntity.ok(boardService.getBoards())
  }

  @GetMapping("/{id}")
  fun getBoard(@PathVariable id: Long) : ResponseEntity<BoardResponse> {
      return ResponseEntity.ok(boardService.getBoard(id))
  }

}